package io.github.joaovpimenta.kazuji.search.ui.suggestion.adapter

import android.view.View
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import io.github.joaovpimenta.kazuji.R
import io.github.joaovpimenta.kazuji.databinding.ItemSearchSuggestionQueryBinding
import io.github.joaovpimenta.kazuji.search.domain.SearchKind
import io.github.joaovpimenta.kazuji.search.ui.suggestion.SearchSuggestionListener
import io.github.joaovpimenta.kazuji.search.ui.suggestion.model.SearchSuggestionItem

fun searchSuggestionQueryAD(
	listener: SearchSuggestionListener,
) =
	adapterDelegateViewBinding<SearchSuggestionItem.RecentQuery, SearchSuggestionItem, ItemSearchSuggestionQueryBinding>(
		{ inflater, parent -> ItemSearchSuggestionQueryBinding.inflate(inflater, parent, false) },
	) {

		val viewClickListener = View.OnClickListener { v ->
			listener.onQueryClick(item.query, SearchKind.SIMPLE, v.id != R.id.button_complete)
		}

		binding.root.setOnClickListener(viewClickListener)
		binding.buttonComplete.setOnClickListener(viewClickListener)

		bind {
			binding.textViewTitle.text = item.query
		}
	}
