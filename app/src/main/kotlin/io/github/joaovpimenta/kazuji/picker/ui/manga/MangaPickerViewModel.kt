package io.github.joaovpimenta.kazuji.picker.ui.manga

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.plus
import io.github.joaovpimenta.kazuji.R
import io.github.joaovpimenta.kazuji.core.parser.MangaDataRepository
import io.github.joaovpimenta.kazuji.core.prefs.AppSettings
import io.github.joaovpimenta.kazuji.favourites.domain.FavouritesRepository
import io.github.joaovpimenta.kazuji.history.data.HistoryRepository
import io.github.joaovpimenta.kazuji.list.domain.MangaListMapper
import io.github.joaovpimenta.kazuji.list.ui.MangaListViewModel
import io.github.joaovpimenta.kazuji.list.ui.model.ListHeader
import io.github.joaovpimenta.kazuji.list.ui.model.ListModel
import io.github.joaovpimenta.kazuji.list.ui.model.LoadingState
import javax.inject.Inject
import kotlinx.coroutines.flow.SharedFlow
import io.github.joaovpimenta.kazuji.local.data.LocalStorageChanges
import io.github.joaovpimenta.kazuji.local.domain.model.LocalManga

@HiltViewModel
class MangaPickerViewModel @Inject constructor(
	private val settings: AppSettings,
	mangaDataRepository: MangaDataRepository,
	private val historyRepository: HistoryRepository,
	private val favouritesRepository: FavouritesRepository,
	private val mangaListMapper: MangaListMapper,
	@LocalStorageChanges localStorageChanges: SharedFlow<LocalManga?>,
) : MangaListViewModel(settings, mangaDataRepository, localStorageChanges) {

	override val content: StateFlow<List<ListModel>>
		get() = flow {
			emit(loadList())
		}.stateIn(viewModelScope + Dispatchers.IO, SharingStarted.Lazily, listOf(LoadingState))

	override fun onRefresh() = Unit

	override fun onRetry() = Unit

	private suspend fun loadList() = buildList {
		val history = historyRepository.getList(0, Int.MAX_VALUE)
		if (history.isNotEmpty()) {
			add(ListHeader(R.string.history))
			mangaListMapper.toListModelList(this, history, settings.listMode)
		}
		val categories = favouritesRepository.observeCategoriesForLibrary().first()
		for (category in categories) {
			val favorites = favouritesRepository.getManga(category.id)
			if (favorites.isNotEmpty()) {
				add(ListHeader(category.title))
				mangaListMapper.toListModelList(this, favorites, settings.listMode)
			}
		}
	}
}
