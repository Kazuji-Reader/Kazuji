package io.github.joaovpimenta.kazuji.favourites.ui.categories.adapter

import io.github.joaovpimenta.kazuji.core.ui.ReorderableListAdapter
import io.github.joaovpimenta.kazuji.favourites.ui.categories.FavouriteCategoriesListListener
import io.github.joaovpimenta.kazuji.list.ui.adapter.ListItemType
import io.github.joaovpimenta.kazuji.list.ui.adapter.ListStateHolderListener
import io.github.joaovpimenta.kazuji.list.ui.adapter.emptyStateListAD
import io.github.joaovpimenta.kazuji.list.ui.adapter.loadingStateAD
import io.github.joaovpimenta.kazuji.list.ui.model.ListModel

class CategoriesAdapter(
	onItemClickListener: FavouriteCategoriesListListener,
	listListener: ListStateHolderListener,
) : ReorderableListAdapter<ListModel>() {

	init {
		addDelegate(ListItemType.CATEGORY_LARGE, categoryAD(onItemClickListener))
		addDelegate(ListItemType.NAV_ITEM, allCategoriesAD(onItemClickListener))
		addDelegate(ListItemType.STATE_EMPTY, emptyStateListAD(listListener))
		addDelegate(ListItemType.STATE_LOADING, loadingStateAD())
	}
}
