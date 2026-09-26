package io.github.joaovpimenta.kazuji.details.ui.pager.pages

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import io.github.joaovpimenta.kazuji.core.util.ext.getItem
import io.github.joaovpimenta.kazuji.list.ui.MangaSelectionDecoration

class PagesSelectionDecoration(context: Context) : MangaSelectionDecoration(context) {

	override fun getItemId(parent: RecyclerView, child: View): Long {
		val holder = parent.getChildViewHolder(child) ?: return RecyclerView.NO_ID
		val item = holder.getItem(PageThumbnail::class.java) ?: return RecyclerView.NO_ID
		return item.page.id
	}
}
