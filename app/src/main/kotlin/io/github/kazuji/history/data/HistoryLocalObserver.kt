package io.github.kazuji.history.data

import dagger.Reusable
import io.github.kazuji.core.db.MangaDatabase
import io.github.kazuji.core.db.entity.toManga
import io.github.kazuji.core.db.entity.toMangaTags
import io.github.kazuji.history.domain.model.MangaWithHistory
import io.github.kazuji.list.domain.ListFilterOption
import io.github.kazuji.list.domain.ListSortOrder
import io.github.kazuji.local.data.index.LocalMangaIndex
import io.github.kazuji.local.domain.LocalObserveMapper
import org.koitharu.kotatsu.parsers.model.Manga
import javax.inject.Inject

@Reusable
class HistoryLocalObserver @Inject constructor(
	localMangaIndex: LocalMangaIndex,
	private val db: MangaDatabase,
) : LocalObserveMapper<HistoryWithManga, MangaWithHistory>(localMangaIndex) {

	fun observeAll(
		order: ListSortOrder,
		filterOptions: Set<ListFilterOption>,
		limit: Int
	) = db.getHistoryDao().observeAll(order, filterOptions, limit).mapToLocal()

	override fun toManga(e: HistoryWithManga) = e.manga.toManga(e.tags.toMangaTags(), null)

	override fun toResult(e: HistoryWithManga, manga: Manga) = MangaWithHistory(
		manga = manga,
		history = e.history.toMangaHistory(),
	)
}
