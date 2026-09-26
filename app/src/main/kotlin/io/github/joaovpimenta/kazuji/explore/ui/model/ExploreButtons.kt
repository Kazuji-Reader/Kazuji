package io.github.joaovpimenta.kazuji.explore.ui.model

import io.github.joaovpimenta.kazuji.list.ui.model.ListModel

data class ExploreButtons(
	val isRandomLoading: Boolean,
) : ListModel {

	override fun areItemsTheSame(other: ListModel): Boolean {
		return other is ExploreButtons
	}
}
