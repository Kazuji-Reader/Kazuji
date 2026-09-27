package io.github.kazuji.bookmarks.ui.adapter

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import io.github.kazuji.bookmarks.domain.Bookmark
import io.github.kazuji.core.ui.list.AdapterDelegateClickListenerAdapter
import io.github.kazuji.core.ui.list.OnListItemClickListener
import io.github.kazuji.databinding.ItemBookmarkLargeBinding
import io.github.kazuji.list.ui.model.ListModel

fun bookmarkLargeAD(
	clickListener: OnListItemClickListener<Bookmark>,
) = adapterDelegateViewBinding<Bookmark, ListModel, ItemBookmarkLargeBinding>(
	{ inflater, parent -> ItemBookmarkLargeBinding.inflate(inflater, parent, false) },
) {
	AdapterDelegateClickListenerAdapter(this, clickListener).attach(itemView)

	bind {
		binding.imageViewThumb.setImageAsync(item)
		binding.progressView.setProgress(item.percent, false)
	}
}
