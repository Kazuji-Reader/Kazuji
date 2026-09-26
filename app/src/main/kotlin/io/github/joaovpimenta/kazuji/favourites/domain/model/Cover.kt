package io.github.joaovpimenta.kazuji.favourites.domain.model

import io.github.joaovpimenta.kazuji.core.model.MangaSource

data class Cover(
	val url: String?,
	val source: String,
) {
	val mangaSource by lazy { MangaSource(source) }
}
