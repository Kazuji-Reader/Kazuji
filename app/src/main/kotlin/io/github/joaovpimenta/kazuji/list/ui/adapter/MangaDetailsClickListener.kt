package io.github.joaovpimenta.kazuji.list.ui.adapter

import android.view.View
import io.github.joaovpimenta.kazuji.core.ui.list.OnListItemClickListener
import io.github.joaovpimenta.kazuji.list.ui.model.MangaListModel
import org.koitharu.kotatsu.parsers.model.Manga
import org.koitharu.kotatsu.parsers.model.MangaTag

interface MangaDetailsClickListener : OnListItemClickListener<MangaListModel> {

	fun onReadClick(manga: Manga, view: View)

	fun onTagClick(manga: Manga, tag: MangaTag, view: View)
}
