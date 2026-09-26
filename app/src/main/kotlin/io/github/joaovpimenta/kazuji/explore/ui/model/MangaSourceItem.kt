package io.github.joaovpimenta.kazuji.explore.ui.model

import io.github.joaovpimenta.kazuji.core.model.MangaSourceInfo
import io.github.joaovpimenta.kazuji.list.ui.model.ListModel
import org.koitharu.kotatsu.parsers.util.longHashCode

data class MangaSourceItem(
	val source: MangaSourceInfo,
	val isGrid: Boolean,
) : ListModel {

	val id: Long = source.name.longHashCode()

	override fun areItemsTheSame(other: ListModel): Boolean {
		return other is MangaSourceItem && other.source == source
	}
}
