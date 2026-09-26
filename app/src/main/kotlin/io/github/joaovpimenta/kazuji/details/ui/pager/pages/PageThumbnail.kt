package io.github.joaovpimenta.kazuji.details.ui.pager.pages

import io.github.joaovpimenta.kazuji.list.ui.model.ListModel
import io.github.joaovpimenta.kazuji.reader.ui.pager.ReaderPage

data class PageThumbnail(
	val isCurrent: Boolean,
	val page: ReaderPage,
) : ListModel {

	val number
		get() = page.index + 1

	override fun areItemsTheSame(other: ListModel): Boolean {
		return other is PageThumbnail && page == other.page
	}
}
