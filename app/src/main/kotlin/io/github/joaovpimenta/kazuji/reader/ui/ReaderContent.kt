package io.github.joaovpimenta.kazuji.reader.ui

import io.github.joaovpimenta.kazuji.reader.ui.pager.ReaderPage

data class ReaderContent(
	val pages: List<ReaderPage>,
	val state: ReaderState?
)