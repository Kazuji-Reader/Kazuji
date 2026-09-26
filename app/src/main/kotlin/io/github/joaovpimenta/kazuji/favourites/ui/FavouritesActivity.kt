package io.github.joaovpimenta.kazuji.favourites.ui

import android.os.Bundle
import io.github.joaovpimenta.kazuji.core.nav.AppRouter
import io.github.joaovpimenta.kazuji.core.ui.FragmentContainerActivity
import io.github.joaovpimenta.kazuji.favourites.ui.list.FavouritesListFragment

class FavouritesActivity : FragmentContainerActivity(FavouritesListFragment::class.java) {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val categoryTitle = intent.getStringExtra(AppRouter.KEY_TITLE)
		if (categoryTitle != null) {
			title = categoryTitle
		}
	}
}
