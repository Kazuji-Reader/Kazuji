package io.github.kazuji.search.ui.multi.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView.RecycledViewPool
import io.github.kazuji.core.ui.BaseListAdapter
import io.github.kazuji.core.ui.list.OnListItemClickListener
import io.github.kazuji.core.ui.list.fastscroll.FastScroller
import io.github.kazuji.list.ui.MangaSelectionDecoration
import io.github.kazuji.list.ui.adapter.ListItemType
import io.github.kazuji.list.ui.adapter.MangaListListener
import io.github.kazuji.list.ui.adapter.buttonFooterAD
import io.github.kazuji.list.ui.adapter.emptyStateListAD
import io.github.kazuji.list.ui.adapter.errorStateListAD
import io.github.kazuji.list.ui.adapter.loadingFooterAD
import io.github.kazuji.list.ui.adapter.loadingStateAD
import io.github.kazuji.list.ui.model.ListModel
import io.github.kazuji.list.ui.size.ItemSizeResolver
import io.github.kazuji.search.ui.multi.SearchResultsListModel

class SearchAdapter(
	listener: MangaListListener,
	itemClickListener: OnListItemClickListener<SearchResultsListModel>,
	sizeResolver: ItemSizeResolver,
	selectionDecoration: MangaSelectionDecoration,
) : BaseListAdapter<ListModel>(), FastScroller.SectionIndexer {

	init {
		val pool = RecycledViewPool()
		addDelegate(
			ListItemType.MANGA_NESTED_GROUP,
			searchResultsAD(
				sharedPool = pool,
				sizeResolver = sizeResolver,
				selectionDecoration = selectionDecoration,
				listener = listener,
				itemClickListener = itemClickListener,
			),
		)
		addDelegate(ListItemType.STATE_LOADING, loadingStateAD())
		addDelegate(ListItemType.FOOTER_LOADING, loadingFooterAD())
		addDelegate(ListItemType.STATE_EMPTY, emptyStateListAD(listener))
		addDelegate(ListItemType.STATE_ERROR, errorStateListAD(listener))
		addDelegate(ListItemType.FOOTER_BUTTON, buttonFooterAD(listener))
	}

	override fun getSectionText(context: Context, position: Int): CharSequence? {
		return (items.getOrNull(position) as? SearchResultsListModel)?.getTitle(context)
	}
}
