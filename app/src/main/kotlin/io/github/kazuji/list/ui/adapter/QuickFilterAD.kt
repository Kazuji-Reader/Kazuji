package io.github.kazuji.list.ui.adapter

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import io.github.kazuji.core.ui.widgets.ChipsView
import io.github.kazuji.databinding.ItemQuickFilterBinding
import io.github.kazuji.list.domain.ListFilterOption
import io.github.kazuji.list.ui.model.ListModel
import io.github.kazuji.list.ui.model.QuickFilter

fun quickFilterAD(
	listener: QuickFilterClickListener,
) = adapterDelegateViewBinding<QuickFilter, ListModel, ItemQuickFilterBinding>(
	{ layoutInflater, parent -> ItemQuickFilterBinding.inflate(layoutInflater, parent, false) }
) {

	binding.chipsTags.onChipClickListener = ChipsView.OnChipClickListener { chip, data ->
		if (data is ListFilterOption) {
			listener.onFilterOptionClick(data)
		}
	}

	bind {
		binding.chipsTags.setChips(item.items)
	}
}
