package io.github.joaovpimenta.kazuji.scrobbling.common.ui.config.adapter

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import io.github.joaovpimenta.kazuji.core.ui.list.AdapterDelegateClickListenerAdapter
import io.github.joaovpimenta.kazuji.core.ui.list.OnListItemClickListener
import io.github.joaovpimenta.kazuji.databinding.ItemScrobblingMangaBinding
import io.github.joaovpimenta.kazuji.list.ui.model.ListModel
import io.github.joaovpimenta.kazuji.scrobbling.common.domain.model.ScrobblingInfo

fun scrobblingMangaAD(
	clickListener: OnListItemClickListener<ScrobblingInfo>,
) = adapterDelegateViewBinding<ScrobblingInfo, ListModel, ItemScrobblingMangaBinding>(
	{ layoutInflater, parent -> ItemScrobblingMangaBinding.inflate(layoutInflater, parent, false) },
) {

	AdapterDelegateClickListenerAdapter(this, clickListener).attach(itemView)

	bind {
		binding.imageViewCover.setImageAsync(item.coverUrl, null)
		binding.textViewTitle.text = item.title
		binding.ratingBar.rating = item.rating * binding.ratingBar.numStars
	}
}
