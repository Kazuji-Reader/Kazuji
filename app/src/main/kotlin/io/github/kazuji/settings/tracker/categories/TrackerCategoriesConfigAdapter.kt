package io.github.kazuji.settings.tracker.categories

import io.github.kazuji.core.model.FavouriteCategory
import io.github.kazuji.core.ui.BaseListAdapter
import io.github.kazuji.core.ui.list.OnListItemClickListener

class TrackerCategoriesConfigAdapter(
	listener: OnListItemClickListener<FavouriteCategory>,
) : BaseListAdapter<FavouriteCategory>() {

	init {
		delegatesManager.addDelegate(trackerCategoryAD(listener))
	}
}
