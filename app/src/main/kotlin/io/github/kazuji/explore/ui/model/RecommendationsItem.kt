package io.github.kazuji.explore.ui.model

import io.github.kazuji.list.ui.model.ListModel
import io.github.kazuji.list.ui.model.MangaCompactListModel

data class RecommendationsItem(
	val manga: List<MangaCompactListModel>
) : ListModel {

	override fun areItemsTheSame(other: ListModel): Boolean {
		return other is RecommendationsItem
	}
}
