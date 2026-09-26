package io.github.joaovpimenta.kazuji.details.ui.scrobbling

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import io.github.joaovpimenta.kazuji.R
import io.github.joaovpimenta.kazuji.core.nav.AppRouter
import io.github.joaovpimenta.kazuji.databinding.ItemScrobblingInfoBinding
import io.github.joaovpimenta.kazuji.list.ui.model.ListModel
import io.github.joaovpimenta.kazuji.scrobbling.common.domain.model.ScrobblingInfo

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
