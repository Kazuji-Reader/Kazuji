package io.github.kazuji.details.ui.scrobbling

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import io.github.kazuji.R
import io.github.kazuji.core.nav.AppRouter
import io.github.kazuji.databinding.ItemScrobblingInfoBinding
import io.github.kazuji.list.ui.model.ListModel
import io.github.kazuji.scrobbling.common.domain.model.ScrobblingInfo

fun scrobblingInfoAD(
	router: AppRouter,
) = adapterDelegateViewBinding<ScrobblingInfo, ListModel, ItemScrobblingInfoBinding>(
	{ layoutInflater, parent -> ItemScrobblingInfoBinding.inflate(layoutInflater, parent, false) },
) {
	binding.root.setOnClickListener {
		router.showScrobblingInfoSheet(bindingAdapterPosition)
	}

	bind {
		binding.imageViewCover.setImageAsync(item.coverUrl)
		binding.textViewTitle.setText(item.scrobbler.titleResId)
		binding.imageViewIcon.setImageResource(item.scrobbler.iconResId)
		binding.ratingBar.rating = item.rating * binding.ratingBar.numStars
		binding.textViewStatus.text = item.status?.let {
			context.resources.getStringArray(R.array.scrobbling_statuses).getOrNull(it.ordinal)
		}
	}
}
