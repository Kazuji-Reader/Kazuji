package io.github.kazuji.list.ui.model

import io.github.kazuji.core.ui.model.MangaOverride
import org.koitharu.kotatsu.parsers.model.Manga

data class MangaCompactListModel(
	override val manga: Manga,
	override val override: MangaOverride?,
	val subtitle: String,
	override val counter: Int,
) : MangaListModel()
