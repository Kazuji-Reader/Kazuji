package io.github.joaovpimenta.kazuji.details.ui.adapter

import android.content.Context
import io.github.joaovpimenta.kazuji.core.ui.BaseListAdapter
import io.github.joaovpimenta.kazuji.core.ui.list.OnListItemClickListener
import io.github.joaovpimenta.kazuji.core.ui.list.fastscroll.FastScroller
import io.github.joaovpimenta.kazuji.details.ui.model.ChapterListItem
import io.github.joaovpimenta.kazuji.list.ui.adapter.ListItemType
import io.github.joaovpimenta.kazuji.list.ui.adapter.listHeaderAD
import io.github.joaovpimenta.kazuji.list.ui.model.ListHeader
import io.github.joaovpimenta.kazuji.list.ui.model.ListModel

class ChaptersAdapter(
	onItemClickListener: OnListItemClickListener<ChapterListItem>,
) : BaseListAdapter<ListModel>(), FastScroller.SectionIndexer {

	private var hasVolumes = false

	init {
		addDelegate(ListItemType.HEADER, listHeaderAD(null))
		addDelegate(ListItemType.CHAPTER_LIST, chapterListItemAD(onItemClickListener))
		addDelegate(ListItemType.CHAPTER_GRID, chapterGridItemAD(onItemClickListener))
	}

	override suspend fun emit(value: List<ListModel>?) {
		super.emit(value)
		hasVolumes = value != null && value.any { it is ListHeader }
	}

	override fun getSectionText(context: Context, position: Int): CharSequence? {
		return if (hasVolumes) {
			findHeader(position)?.getText(context)
		} else {
			val chapter = (items.getOrNull(position) as? ChapterListItem)?.chapter ?: return null
			chapter.numberString()
		}
	}
}
