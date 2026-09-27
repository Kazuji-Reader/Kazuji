package io.github.kazuji.explore.ui.adapter

import io.github.kazuji.core.ui.BaseListAdapter
import io.github.kazuji.core.ui.list.OnListItemClickListener
import io.github.kazuji.explore.ui.model.MangaSourceItem
import io.github.kazuji.list.ui.adapter.ListItemType
import io.github.kazuji.list.ui.adapter.emptyHintAD
import io.github.kazuji.list.ui.adapter.listHeaderAD
import io.github.kazuji.list.ui.adapter.loadingStateAD
import io.github.kazuji.list.ui.model.ListModel
import org.koitharu.kotatsu.parsers.model.Manga

class ExploreAdapter(
	listener: ExploreListEventListener,
	clickListener: OnListItemClickListener<MangaSourceItem>,
	mangaClickListener: OnListItemClickListener<Manga>,
) : BaseListAdapter<ListModel>() {

	init {
		addDelegate(ListItemType.EXPLORE_BUTTONS, exploreButtonsAD(listener))
		addDelegate(
			ListItemType.EXPLORE_SUGGESTION,
			exploreRecommendationItemAD(mangaClickListener),
		)
		addDelegate(ListItemType.HEADER, listHeaderAD(listener))
		addDelegate(ListItemType.EXPLORE_SOURCE_LIST, exploreSourceListItemAD(clickListener))
		addDelegate(ListItemType.EXPLORE_SOURCE_GRID, exploreSourceGridItemAD(clickListener))
		addDelegate(ListItemType.HINT_EMPTY, emptyHintAD(listener))
		addDelegate(ListItemType.STATE_LOADING, loadingStateAD())
	}
}
