package io.github.joaovpimenta.kazuji.favourites.ui.categories

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import io.github.joaovpimenta.kazuji.core.model.FavouriteCategory
import io.github.joaovpimenta.kazuji.core.ui.list.OnListItemClickListener

interface FavouriteCategoriesListListener : OnListItemClickListener<FavouriteCategory?> {

	fun onDragHandleTouch(holder: RecyclerView.ViewHolder): Boolean

	fun onEditClick(item: FavouriteCategory, view: View)

	fun onShowAllClick(isChecked: Boolean)
}
