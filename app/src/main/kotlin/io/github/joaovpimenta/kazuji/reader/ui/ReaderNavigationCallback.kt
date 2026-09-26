package io.github.joaovpimenta.kazuji.reader.ui

import io.github.joaovpimenta.kazuji.bookmarks.domain.Bookmark
import org.koitharu.kotatsu.parsers.model.MangaChapter
import io.github.joaovpimenta.kazuji.reader.ui.pager.ReaderPage

interface ReaderNavigationCallback {

	fun onPageSelected(page: ReaderPage): Boolean

	fun onChapterSelected(chapter: MangaChapter): Boolean

	fun onBookmarkSelected(bookmark: Bookmark): Boolean = onPageSelected(
		ReaderPage(bookmark.toMangaPage(), bookmark.page, bookmark.chapterId),
	)
}
