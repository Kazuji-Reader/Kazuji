package io.github.kazuji.settings.sources.adapter

import io.github.kazuji.core.ui.list.OnTipCloseListener
import io.github.kazuji.settings.sources.model.SourceConfigItem

interface SourceConfigListener : OnTipCloseListener<SourceConfigItem.Tip> {

	fun onItemSettingsClick(item: SourceConfigItem.SourceItem)

	fun onItemLiftClick(item: SourceConfigItem.SourceItem)

	fun onItemShortcutClick(item: SourceConfigItem.SourceItem)

	fun onItemPinClick(item: SourceConfigItem.SourceItem)

	fun onItemEnabledChanged(item: SourceConfigItem.SourceItem, isEnabled: Boolean)
}
