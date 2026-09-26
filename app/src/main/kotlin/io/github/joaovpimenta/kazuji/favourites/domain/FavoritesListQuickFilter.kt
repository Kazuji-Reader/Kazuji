package io.github.joaovpimenta.kazuji.favourites.domain

import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import io.github.joaovpimenta.kazuji.core.os.NetworkState
import io.github.joaovpimenta.kazuji.core.prefs.AppSettings
import io.github.joaovpimenta.kazuji.list.domain.ListFilterOption
import io.github.joaovpimenta.kazuji.list.domain.MangaListQuickFilter

class FavoritesListQuickFilter @AssistedInject constructor(
	@Assisted private val categoryId: Long,
	private val settings: AppSettings,
	private val repository: FavouritesRepository,
	networkState: NetworkState,
) : MangaListQuickFilter(settings) {

	init {
		setFilterOption(ListFilterOption.Downloaded, !networkState.value)
	}

	override suspend fun getAvailableFilterOptions(): List<ListFilterOption> = buildList {
		add(ListFilterOption.Downloaded)
		if (settings.isTrackerEnabled) {
			add(ListFilterOption.Macro.NEW_CHAPTERS)
		}
		add(ListFilterOption.Macro.COMPLETED)
		repository.findPopularSources(categoryId, 3).mapTo(this) {
			ListFilterOption.Source(it)
		}
	}

	@AssistedFactory
	interface Factory {

		fun create(categoryId: Long): FavoritesListQuickFilter
	}
}
