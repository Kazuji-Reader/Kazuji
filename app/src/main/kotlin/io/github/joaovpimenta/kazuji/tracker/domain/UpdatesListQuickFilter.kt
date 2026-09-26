package io.github.joaovpimenta.kazuji.tracker.domain

import io.github.joaovpimenta.kazuji.core.prefs.AppSettings
import io.github.joaovpimenta.kazuji.favourites.domain.FavouritesRepository
import io.github.joaovpimenta.kazuji.list.domain.ListFilterOption
import io.github.joaovpimenta.kazuji.list.domain.MangaListQuickFilter
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
