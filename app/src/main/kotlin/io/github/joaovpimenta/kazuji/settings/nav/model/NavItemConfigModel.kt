package io.github.joaovpimenta.kazuji.settings.nav.model

import androidx.annotation.StringRes
import io.github.joaovpimenta.kazuji.core.prefs.NavItem
import io.github.joaovpimenta.kazuji.list.ui.model.ListModel

data class NavItemConfigModel(
	val item: NavItem,
	@StringRes val disabledHintResId: Int,
) : ListModel {

	override fun areItemsTheSame(other: ListModel): Boolean {
		return other is NavItemConfigModel && other.item == item
	}
}
