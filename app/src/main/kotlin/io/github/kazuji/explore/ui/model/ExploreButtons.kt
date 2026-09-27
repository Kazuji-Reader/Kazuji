package io.github.kazuji.explore.ui.model

import io.github.kazuji.list.ui.model.ListModel

data class ExploreButtons(
	val isRandomLoading: Boolean,
) : ListModel {

	override fun areItemsTheSame(other: ListModel): Boolean {
		return other is ExploreButtons
	}
}
