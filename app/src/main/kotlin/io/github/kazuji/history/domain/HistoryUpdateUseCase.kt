package io.github.kazuji.history.domain

import android.util.Log
import io.github.kazuji.core.db.MangaDatabase
import io.github.kazuji.core.parser.MangaRepository
import io.github.kazuji.core.prefs.AppSettings
import io.github.kazuji.core.util.ext.printStackTraceDebug
import io.github.kazuji.core.util.ext.processLifecycleScope
import io.github.kazuji.download.data.repository.DownloadQueueRepository
import io.github.kazuji.download.domain.usecase.SmartDownloadUseCase
import io.github.kazuji.download.ui.worker.DownloadWorker
import io.github.kazuji.history.data.HistoryRepository
import io.github.kazuji.local.data.LocalMangaRepository
import io.github.kazuji.local.domain.DeleteReadChaptersUseCase
import io.github.kazuji.reader.ui.ReaderState
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koitharu.kotatsu.parsers.model.Manga
import org.koitharu.kotatsu.parsers.util.runCatchingCancellable
import javax.inject.Inject

class HistoryUpdateUseCase @Inject constructor(
	private val historyRepository: HistoryRepository,
	private val settings: AppSettings,
	private val db: MangaDatabase,
	private val downloadQueueRepository: DownloadQueueRepository,
	private val deleteReadChaptersUseCase: DeleteReadChaptersUseCase,
	private val localMangaRepository: LocalMangaRepository,
	private val downloadScheduler: DownloadWorker.Scheduler,
	private val mangaRepositoryFactory: MangaRepository.Factory,
	private val smartDownloadUseCase: SmartDownloadUseCase,
) {

	private var lastCheckedChapterId: Long = -1L

	suspend operator fun invoke(manga: Manga, readerState: ReaderState, percent: Float) {
		historyRepository.addOrUpdate(
			manga = manga,
			chapterId = readerState.chapterId,
			page = readerState.page,
			scroll = readerState.scroll,
			percent = percent,
			force = false,
		)
		if (settings.isAutoDownloadNextChapterEnabled && lastCheckedChapterId != readerState.chapterId) {
			Log.d("SmartDownloads", "Chapter changed, triggering smart download for ${manga.title}")
			lastCheckedChapterId = readerState.chapterId
			smartDownloadUseCase(manga, readerState.chapterId)
		}
	}

	fun invokeAsync(
		manga: Manga,
		readerState: ReaderState,
		percent: Float
	) = processLifecycleScope.launch(Dispatchers.IO, CoroutineStart.ATOMIC) {
		runCatchingCancellable {
			withContext(NonCancellable) {
				invoke(manga, readerState, percent)
			}
		}.onFailure {
			it.printStackTraceDebug("HistoryUpdateUseCase::invokeAsync")
		}
	}
}
