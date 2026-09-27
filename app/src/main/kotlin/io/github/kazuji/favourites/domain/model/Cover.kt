package io.github.kazuji.favourites.domain.model

import io.github.kazuji.core.model.MangaSource

data class Cover(
	val url: String?,
	val source: String,
) {
	val mangaSource by lazy { MangaSource(source) }
}
