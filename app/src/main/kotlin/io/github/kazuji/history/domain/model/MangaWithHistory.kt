package io.github.kazuji.history.domain.model

import io.github.kazuji.core.model.MangaHistory
import org.koitharu.kotatsu.parsers.model.Manga

data class MangaWithHistory(
	val manga: Manga,
	val history: MangaHistory
)
