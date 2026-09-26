package io.github.joaovpimenta.kazuji.favourites.ui.categories.select.adapter

import io.github.joaovpimenta.kazuji.core.ui.BaseListAdapter
import io.github.joaovpimenta.kazuji.core.ui.list.OnListItemClickListener
import io.github.joaovpimenta.kazuji.favourites.ui.categories.select.model.MangaCategoryItem
import io.github.joaovpimenta.kazuji.list.ui.adapter.ListItemType
import io.github.joaovpimenta.kazuji.list.ui.adapter.emptyStateListAD
import io.github.joaovpimenta.kazuji.list.ui.adapter.loadingStateAD
import io.github.joaovpimenta.kazuji.list.ui.model.ListModel

class MangaCategoriesAdapter(
	clickListener: OnListItemClickListener<MangaCategoryItem>,
) : BaseListAdapter<ListModel>() {

	init {
		addDelegate(ListItemType.NAV_ITEM, mangaCategoryAD(clickListener))
		addDelegate(ListItemType.STATE_LOADING, loadingStateAD())
		addDelegate(ListItemType.STATE_EMPTY, emptyStateListAD(null))
	}
}
