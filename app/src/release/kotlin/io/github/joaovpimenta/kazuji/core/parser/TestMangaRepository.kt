package io.github.joaovpimenta.kazuji.core.parser

import io.github.joaovpimenta.kazuji.core.cache.MemoryContentCache
import io.github.joaovpimenta.kazuji.core.model.TestMangaSource
import org.koitharu.kotatsu.parsers.MangaLoaderContext

@Suppress("unused")
class TestMangaRepository(
	private val loaderContext: MangaLoaderContext,
	cache: MemoryContentCache
) : EmptyMangaRepository(TestMangaSource)
