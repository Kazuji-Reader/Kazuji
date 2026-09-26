package io.github.joaovpimenta.kazuji.stats.ui

import android.content.res.ColorStateList
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import io.github.joaovpimenta.kazuji.R
import io.github.joaovpimenta.kazuji.core.ui.list.OnListItemClickListener
import io.github.joaovpimenta.kazuji.core.util.KazujiColors
import io.github.joaovpimenta.kazuji.databinding.ItemStatsBinding
import io.github.joaovpimenta.kazuji.stats.domain.StatsRecord
import org.koitharu.kotatsu.parsers.model.Manga

fun statsAD(
	listener: OnListItemClickListener<Manga>,
) = adapterDelegateViewBinding<StatsRecord, StatsRecord, ItemStatsBinding>(
	{ layoutInflater, parent -> ItemStatsBinding.inflate(layoutInflater, parent, false) },
) {

	binding.root.setOnClickListener { v ->
		item.manga?.let { listener.onItemClick(it, v) }
	}

	bind {
		binding.textViewTitle.text = item.manga?.title ?: item.tagName ?: getString(R.string.other_manga)
		binding.textViewSummary.text = item.time.format(context.resources)
		binding.imageViewBadge.imageTintList = ColorStateList.valueOf(KazujiColors.ofManga(context, item.manga))
		binding.root.isClickable = item.manga != null
	}
}
