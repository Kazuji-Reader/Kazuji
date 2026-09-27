package io.github.kazuji.scrobbling.common.ui.selector.adapter

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import io.github.kazuji.R
import io.github.kazuji.core.ui.list.OnListItemClickListener
import io.github.kazuji.core.util.ext.textAndVisible
import io.github.kazuji.databinding.ItemMangaListBinding
import io.github.kazuji.list.ui.model.ListModel
import io.github.kazuji.scrobbling.common.domain.model.ScrobblerManga

fun scrobblingMangaAD(
	clickListener: OnListItemClickListener<ScrobblerManga>,
) = adapterDelegateViewBinding<ScrobblerManga, ListModel, ItemMangaListBinding>(
	{ inflater, parent -> ItemMangaListBinding.inflate(inflater, parent, false) },
) {
	itemView.setOnClickListener {
		clickListener.onItemClick(item, it)
	}

	bind {
		binding.textViewTitle.text = item.name
		val endIcon = if (item.isBestMatch) R.drawable.ic_star_small else 0
		binding.textViewTitle.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, endIcon, 0)
		binding.textViewSubtitle.textAndVisible = item.altName
		binding.imageViewCover.setImageAsync(item.cover, null)
	}
}
