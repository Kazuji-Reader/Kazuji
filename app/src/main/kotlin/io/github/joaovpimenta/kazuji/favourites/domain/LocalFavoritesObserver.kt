package io.github.joaovpimenta.kazuji.favourites.domain

import dagger.Reusable
import kotlinx.coroutines.flow.Flow
import io.github.joaovpimenta.kazuji.core.db.MangaDatabase
import io.github.joaovpimenta.kazuji.core.db.entity.toManga
import io.github.joaovpimenta.kazuji.core.db.entity.toMangaTags
import io.github.joaovpimenta.kazuji.favourites.data.FavouriteManga
import io.github.joaovpimenta.kazuji.list.domain.ListFilterOption
import io.github.joaovpimenta.kazuji.list.domain.ListSortOrder
import io.github.joaovpimenta.kazuji.local.data.index.LocalMangaIndex
import io.github.joaovpimenta.kazuji.local.domain.LocalObserveMapper
import org.koitharu.kotatsu.parsers.model.Manga
import javax.inject.Inject

@Reusable
class LocalFavoritesObserver @Inject constructor(
	localMangaIndex: LocalMangaIndex,
	private val db: MangaDatabase,
) : LocalObserveMapper<FavouriteManga, Manga>(localMangaIndex) {

	fun observeAll(
		order: ListSortOrder,
		filterOptions: Set<ListFilterOption>,
		limit: Int
	): Flow<List<Manga>> = db.getFavouritesDao().observeAll(order, filterOptions, limit).mapToLocal()

	fun observeAll(
		categoryId: Long,
		order: ListSortOrder,
		filterOptions: Set<ListFilterOption>,
		limit: Int
	): Flow<List<Manga>> = db.getFavouritesDao().observeAll(categoryId, order, filterOptions, limit).mapToLocal()

	override fun toManga(e: FavouriteManga) = e.manga.toManga(e.tags.toMangaTags(), null)

	override fun toResult(e: FavouriteManga, manga: Manga) = manga
}
