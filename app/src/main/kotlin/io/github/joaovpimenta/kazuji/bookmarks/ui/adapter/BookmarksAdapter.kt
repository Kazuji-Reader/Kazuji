package io.github.joaovpimenta.kazuji.bookmarks.ui.adapter

import android.content.Context
import io.github.joaovpimenta.kazuji.bookmarks.domain.Bookmark
import io.github.joaovpimenta.kazuji.core.ui.BaseListAdapter
import io.github.joaovpimenta.kazuji.core.ui.list.OnListItemClickListener
import io.github.joaovpimenta.kazuji.core.ui.list.fastscroll.FastScroller
import io.github.joaovpimenta.kazuji.list.ui.adapter.ListHeaderClickListener
import io.github.joaovpimenta.kazuji.list.ui.adapter.ListItemType
import io.github.joaovpimenta.kazuji.list.ui.adapter.emptyStateListAD
import io.github.joaovpimenta.kazuji.list.ui.adapter.errorStateListAD
import io.github.joaovpimenta.kazuji.list.ui.adapter.listHeaderAD
import io.github.joaovpimenta.kazuji.list.ui.adapter.loadingFooterAD
import io.github.joaovpimenta.kazuji.list.ui.adapter.loadingStateAD
import io.github.joaovpimenta.kazuji.list.ui.model.ListModel

class BookmarksAdapter(
	clickListener: OnListItemClickListener<Bookmark>,
	headerClickListener: ListHeaderClickListener?,
) : BaseListAdapter<ListModel>(), FastScroller.SectionIndexer {

	init {
		addDelegate(ListItemType.PAGE_THUMB, bookmarkLargeAD(clickListener))
		addDelegate(ListItemType.HEADER, listHeaderAD(headerClickListener))
		addDelegate(ListItemType.STATE_ERROR, errorStateListAD(null))
		addDelegate(ListItemType.FOOTER_LOADING, loadingFooterAD())
		addDelegate(ListItemType.STATE_LOADING, loadingStateAD())
		addDelegate(ListItemType.STATE_EMPTY, emptyStateListAD(null))
	}

	override fun getSectionText(context: Context, position: Int): CharSequence? {
		return findHeader(position)?.getText(context)
	}
}
