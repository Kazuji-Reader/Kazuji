package io.github.joaovpimenta.kazuji.list.ui

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import io.github.joaovpimenta.kazuji.R
import io.github.joaovpimenta.kazuji.core.nav.router
import io.github.joaovpimenta.kazuji.favourites.ui.list.FavouritesListFragment
import io.github.joaovpimenta.kazuji.history.ui.HistoryListFragment
import io.github.joaovpimenta.kazuji.list.ui.config.ListConfigSection
import io.github.joaovpimenta.kazuji.suggestions.ui.SuggestionsFragment
import io.github.joaovpimenta.kazuji.tracker.ui.updates.UpdatesFragment

class MangaListMenuProvider(
	private val fragment: Fragment,
) : MenuProvider {

	override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
		menuInflater.inflate(R.menu.opt_list, menu)
	}

	override fun onMenuItemSelected(menuItem: MenuItem): Boolean = when (menuItem.itemId) {
		R.id.action_list_mode -> {
			val section: ListConfigSection = when (fragment) {
				is HistoryListFragment -> ListConfigSection.History
				is SuggestionsFragment -> ListConfigSection.Suggestions
				is FavouritesListFragment -> ListConfigSection.Favorites(fragment.categoryId)
				is UpdatesFragment -> ListConfigSection.Updated
				else -> ListConfigSection.General
			}
			fragment.router.showListConfigSheet(section)
			true
		}

		else -> false
	}
}
