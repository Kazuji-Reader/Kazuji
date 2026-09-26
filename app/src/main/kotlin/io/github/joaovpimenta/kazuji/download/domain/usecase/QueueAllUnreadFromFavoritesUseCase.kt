package io.github.joaovpimenta.kazuji.download.domain.usecase

import androidx.work.WorkManager
import io.github.joaovpimenta.kazuji.core.db.MangaDatabase
import io.github.joaovpimenta.kazuji.core.util.ext.printStackTraceDebug
import io.github.joaovpimenta.kazuji.download.ui.worker.DownloadSchedulerWorker
import io.github.joaovpimenta.kazuji.favourites.data.toManga
import io.github.joaovpimenta.kazuji.mihon.parsers.util.runCatchingCancellable
import javax.inject.Inject

class QueueAllUnreadFromFavoritesUseCase @Inject constructor(
    private val db: MangaDatabase,
    private val addUnreadToQueueUseCase: AddUnreadToQueueUseCase,
    private val workManager: WorkManager,
) {
    suspend operator fun invoke(wifiOnly: Boolean, chargingOnly: Boolean, offPeakOnly: Boolean) {
        runCatchingCancellable {
            val favorites = db.getFavouritesDao().findAll()
            favorites.forEach { favorite ->
                addUnreadToQueueUseCase(
                    manga = favorite.toManga(),
                    wifiOnly = wifiOnly,
                    chargingOnly = chargingOnly,
                    offPeakOnly = offPeakOnly,
                )
            }
            DownloadSchedulerWorker.enqueue(workManager)
        }.onFailure {
            it.printStackTraceDebug()
        }
    }
}
