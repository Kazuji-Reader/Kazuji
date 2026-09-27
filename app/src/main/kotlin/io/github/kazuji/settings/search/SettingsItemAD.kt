package io.github.kazuji.settings.search

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import io.github.kazuji.R
import io.github.kazuji.core.ui.list.AdapterDelegateClickListenerAdapter
import io.github.kazuji.core.ui.list.OnListItemClickListener
import io.github.kazuji.core.util.ext.textAndVisible
import io.github.kazuji.databinding.ItemPreferenceBinding

fun settingsItemAD(
	listener: OnListItemClickListener<SettingsItem>,
) = adapterDelegateViewBinding<SettingsItem, SettingsItem, ItemPreferenceBinding>(
	{ layoutInflater, parent -> ItemPreferenceBinding.inflate(layoutInflater, parent, false) },
) {

	AdapterDelegateClickListenerAdapter(this, listener).attach()
	val breadcrumbsSeparator = getString(R.string.breadcrumbs_separator)

	bind {
		binding.textViewTitle.text = item.title
		binding.textViewSummary.textAndVisible = item.breadcrumbs.joinToString(breadcrumbsSeparator)
	}
}
