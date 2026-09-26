package io.github.joaovpimenta.kazuji.settings.tracker.categories

import io.github.joaovpimenta.kazuji.core.model.FavouriteCategory
import io.github.joaovpimenta.kazuji.core.ui.BaseListAdapter
import io.github.joaovpimenta.kazuji.core.ui.list.OnListItemClickListener

class TrackerCategoriesConfigAdapter(
	listener: OnListItemClickListener<FavouriteCategory>,
) : BaseListAdapter<FavouriteCategory>() {

	init {
		delegatesManager.addDelegate(trackerCategoryAD(listener))
	}
}
