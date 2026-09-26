package io.github.joaovpimenta.kazuji.stats.ui

import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.joaovpimenta.kazuji.R
import io.github.joaovpimenta.kazuji.core.model.FavouriteCategory
import io.github.joaovpimenta.kazuji.core.ui.BaseViewModel
import io.github.joaovpimenta.kazuji.core.ui.util.ReversibleAction
import io.github.joaovpimenta.kazuji.core.util.ext.MutableEventFlow
import io.github.joaovpimenta.kazuji.core.util.ext.call
import io.github.joaovpimenta.kazuji.favourites.domain.FavouritesRepository
import io.github.joaovpimenta.kazuji.stats.data.StatsRepository
import io.github.joaovpimenta.kazuji.stats.domain.StatsPeriod
import io.github.joaovpimenta.kazuji.stats.domain.StatsRecord
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.take
import javax.inject.Inject

@HiltViewModel
class StatsViewModel @Inject constructor(
	private val repository: StatsRepository,
	favouritesRepository: FavouritesRepository,
) : BaseViewModel() {

	val period = MutableStateFlow(StatsPeriod.WEEK)
	val byGenre = MutableStateFlow(false)
	val onActionDone = MutableEventFlow<ReversibleAction>()
	val selectedCategories = MutableStateFlow<Set<Long>>(emptySet())
	val favoriteCategories = favouritesRepository.observeCategories()
		.take(1)

	val readingStats = MutableStateFlow<List<StatsRecord>>(emptyList())

	init {
		launchJob(Dispatchers.IO) {
			combine(
				period,
				selectedCategories,
				byGenre,
				::Triple,
			).collectLatest { (p, categories, genre) ->
				readingStats.value = withLoading {
					repository.getReadingStats(p, categories, genre)
				}
			}
		}
	}

	fun setCategoryChecked(category: FavouriteCategory, checked: Boolean) {
		val snapshot = selectedCategories.value.toMutableSet()
		if (checked) {
			snapshot.add(category.id)
		} else {
			snapshot.remove(category.id)
		}
		selectedCategories.value = snapshot
	}

	fun clearStats() {
		launchLoadingJob(Dispatchers.IO) {
			repository.clearStats()
			readingStats.value = emptyList()
			onActionDone.call(ReversibleAction(R.string.stats_cleared, null))
		}
	}
}
