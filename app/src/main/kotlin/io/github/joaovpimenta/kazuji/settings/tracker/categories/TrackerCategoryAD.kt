package io.github.joaovpimenta.kazuji.settings.tracker.categories

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import io.github.joaovpimenta.kazuji.core.model.FavouriteCategory
import io.github.joaovpimenta.kazuji.core.ui.list.AdapterDelegateClickListenerAdapter
import io.github.joaovpimenta.kazuji.core.ui.list.OnListItemClickListener
import io.github.joaovpimenta.kazuji.databinding.ItemCategoryCheckableMultipleBinding

fun trackerCategoryAD(
	listener: OnListItemClickListener<FavouriteCategory>,
) = adapterDelegateViewBinding<FavouriteCategory, FavouriteCategory, ItemCategoryCheckableMultipleBinding>(
	{ layoutInflater, parent -> ItemCategoryCheckableMultipleBinding.inflate(layoutInflater, parent, false) },
) {
	val eventListener = AdapterDelegateClickListenerAdapter(this, listener)
	itemView.setOnClickListener(eventListener)

	bind {
		binding.root.text = item.title
		binding.root.isChecked = item.isTrackingEnabled
	}
}
