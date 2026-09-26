package io.github.joaovpimenta.kazuji.settings.nav.model

import io.github.joaovpimenta.kazuji.list.ui.model.ListModel

data class NavItemAddModel(
	val canAdd: Boolean,
) : ListModel {

	override fun areItemsTheSame(other: ListModel): Boolean = other is NavItemAddModel
}
