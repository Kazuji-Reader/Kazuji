package io.github.kazuji.history.ui

import android.content.Context
import io.github.kazuji.core.ui.list.fastscroll.FastScroller
import io.github.kazuji.list.ui.adapter.MangaListAdapter
import io.github.kazuji.list.ui.adapter.MangaListListener
import io.github.kazuji.list.ui.size.ItemSizeResolver

class HistoryListAdapter(
	listener: MangaListListener,
	sizeResolver: ItemSizeResolver,
) : MangaListAdapter(listener, sizeResolver), FastScroller.SectionIndexer {

	override fun getSectionText(context: Context, position: Int): CharSequence? {
		return findHeader(position)?.getText(context)
	}
}
