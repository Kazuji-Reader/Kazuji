package io.github.joaovpimenta.kazuji.filter.ui.model

import io.github.joaovpimenta.kazuji.list.ui.ListModelDiffCallback
import io.github.joaovpimenta.kazuji.list.ui.model.ListModel
import org.koitharu.kotatsu.parsers.model.MangaTag

data class TagCatalogItem(
	val tag: MangaTag,
	val isChecked: Boolean,
) : ListModel {

	override fun areItemsTheSame(other: ListModel): Boolean {
		return other is TagCatalogItem && other.tag == tag
	}

	override fun getChangePayload(previousState: ListModel): Any? {
		return if (previousState is TagCatalogItem && previousState.isChecked != isChecked) {
			ListModelDiffCallback.PAYLOAD_CHECKED_CHANGED
		} else {
			super.getChangePayload(previousState)
		}
	}
}
