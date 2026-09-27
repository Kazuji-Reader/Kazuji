package io.github.kazuji.tracker.domain

import io.github.kazuji.core.prefs.AppSettings
import io.github.kazuji.favourites.domain.FavouritesRepository
import io.github.kazuji.list.domain.ListFilterOption
import io.github.kazuji.list.domain.MangaListQuickFilter
import javax.inject.Inject

class UpdatesListQuickFilter @Inject constructor(
	private val favouritesRepository: FavouritesRepository,
	settings: AppSettings,
) : MangaListQuickFilter(settings) {

	override suspend fun getAvailableFilterOptions(): List<ListFilterOption> =
		favouritesRepository.getMostUpdatedCategories(
			limit = 4,
		).map {
			ListFilterOption.Favorite(it)
		}
}
