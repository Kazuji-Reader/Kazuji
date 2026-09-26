package io.github.joaovpimenta.kazuji.favourites.data

import io.github.joaovpimenta.kazuji.core.db.entity.toManga
import io.github.joaovpimenta.kazuji.core.db.entity.toMangaTags
import io.github.joaovpimenta.kazuji.core.model.FavouriteCategory
import io.github.joaovpimenta.kazuji.list.domain.ListSortOrder
import java.time.Instant

fun FavouriteCategoryEntity.toFavouriteCategory(id: Long = categoryId.toLong()) = FavouriteCategory(
	id = id,
	title = title,
	sortKey = sortKey,
	order = ListSortOrder(order, ListSortOrder.NEWEST),
	createdAt = Instant.ofEpochMilli(createdAt),
	isTrackingEnabled = track,
	isVisibleInLibrary = isVisibleInLibrary,
)

fun FavouriteManga.toManga() = manga.toManga(tags.toMangaTags(), null)

fun Collection<FavouriteManga>.toMangaList() = map { it.toManga() }
