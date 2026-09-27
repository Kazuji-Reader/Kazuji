package io.github.kazuji.widget.shelf.adapter

import io.github.kazuji.core.ui.BaseListAdapter
import io.github.kazuji.core.ui.list.OnListItemClickListener
import io.github.kazuji.widget.shelf.model.CategoryItem

class CategorySelectAdapter(
	clickListener: OnListItemClickListener<CategoryItem>
) : BaseListAdapter<CategoryItem>() {

	init {
		delegatesManager.addDelegate(categorySelectItemAD(clickListener))
	}
}
