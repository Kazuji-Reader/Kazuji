package io.github.joaovpimenta.kazuji.history.ui

import android.content.Context
import io.github.joaovpimenta.kazuji.core.ui.list.fastscroll.FastScroller
import io.github.joaovpimenta.kazuji.list.ui.adapter.MangaListAdapter
import io.github.joaovpimenta.kazuji.list.ui.adapter.MangaListListener
import io.github.joaovpimenta.kazuji.list.ui.size.ItemSizeResolver

class HistoryListAdapter(
	listener: MangaListListener,
	sizeResolver: ItemSizeResolver,
) : MangaListAdapter(listener, sizeResolver), FastScroller.SectionIndexer {

	override fun getSectionText(context: Context, position: Int): CharSequence? {
		return findHeader(position)?.getText(context)
	}
}
