package io.github.kazuji.reader.ui

import io.github.kazuji.reader.ui.pager.ReaderPage

data class ReaderContent(
	val pages: List<ReaderPage>,
	val state: ReaderState?
)