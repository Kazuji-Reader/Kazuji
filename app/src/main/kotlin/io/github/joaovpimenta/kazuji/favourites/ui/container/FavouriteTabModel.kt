package io.github.joaovpimenta.kazuji.favourites.ui.container

import io.github.joaovpimenta.kazuji.list.ui.model.ListModel

data class FavouriteTabModel(
	val id: Long,
	val title: String?,
) : ListModel {

	override fun areItemsTheSame(other: ListModel): Boolean {
		return other is FavouriteTabModel && other.id == id
	}
}
