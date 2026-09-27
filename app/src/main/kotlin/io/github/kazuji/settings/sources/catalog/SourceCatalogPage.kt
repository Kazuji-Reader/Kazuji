package io.github.kazuji.settings.sources.catalog

import io.github.kazuji.list.ui.ListModelDiffCallback
import io.github.kazuji.list.ui.model.ListModel
import org.koitharu.kotatsu.parsers.model.ContentType

data class SourceCatalogPage(
	val type: ContentType,
	val items: List<SourceCatalogItem>,
) : ListModel {

	override fun areItemsTheSame(other: ListModel): Boolean {
		return other is SourceCatalogPage && other.type == type
	}

	override fun getChangePayload(previousState: ListModel): Any {
		return ListModelDiffCallback.PAYLOAD_NESTED_LIST_CHANGED
	}
}
