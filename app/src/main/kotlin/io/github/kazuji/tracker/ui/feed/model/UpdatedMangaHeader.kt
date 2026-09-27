package io.github.kazuji.tracker.ui.feed.model

import io.github.kazuji.list.ui.ListModelDiffCallback
import io.github.kazuji.list.ui.model.ListModel
import io.github.kazuji.list.ui.model.MangaListModel

data class UpdatedMangaHeader(
	val list: List<MangaListModel>,
) : ListModel {

	override fun areItemsTheSame(other: ListModel): Boolean {
		return other is UpdatedMangaHeader
	}

	override fun getChangePayload(previousState: ListModel): Any {
		return ListModelDiffCallback.PAYLOAD_NESTED_LIST_CHANGED
	}
}
