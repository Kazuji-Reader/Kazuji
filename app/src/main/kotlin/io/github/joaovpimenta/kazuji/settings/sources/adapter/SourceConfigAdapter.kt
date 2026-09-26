package io.github.joaovpimenta.kazuji.settings.sources.adapter

import io.github.joaovpimenta.kazuji.core.ui.ReorderableListAdapter
import io.github.joaovpimenta.kazuji.settings.sources.model.SourceConfigItem

class SourceConfigAdapter(
	listener: SourceConfigListener,
) : ReorderableListAdapter<SourceConfigItem>() {

	init {
		with(delegatesManager) {
			addDelegate(sourceConfigItemDelegate2(listener))
			addDelegate(sourceConfigEmptySearchDelegate())
			addDelegate(sourceConfigTipDelegate(listener))
		}
	}
}
