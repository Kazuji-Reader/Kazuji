package io.github.joaovpimenta.kazuji.filter.ui.model

import io.github.joaovpimenta.kazuji.core.ui.widgets.ChipsView
import org.koitharu.kotatsu.parsers.model.SortOrder

data class FilterHeaderModel(
	val chips: Collection<ChipsView.ChipModel>,
	val sortOrder: SortOrder?,
	val isFilterApplied: Boolean,
) {

	val textSummary: String
		get() = chips.mapNotNull { if (it.isChecked) it.title else null }.joinToString()
}
