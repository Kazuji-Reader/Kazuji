package io.github.joaovpimenta.kazuji.scrobbling.common.ui.selector.adapter

import io.github.joaovpimenta.kazuji.core.ui.BaseListAdapter
import io.github.joaovpimenta.kazuji.core.ui.list.OnListItemClickListener
import io.github.joaovpimenta.kazuji.list.ui.adapter.ListItemType
import io.github.joaovpimenta.kazuji.list.ui.adapter.ListStateHolderListener
import io.github.joaovpimenta.kazuji.list.ui.adapter.loadingFooterAD
import io.github.joaovpimenta.kazuji.list.ui.adapter.loadingStateAD
import io.github.joaovpimenta.kazuji.list.ui.model.ListModel
import io.github.joaovpimenta.kazuji.scrobbling.common.domain.model.ScrobblerManga

class ScrobblerSelectorAdapter(
	clickListener: OnListItemClickListener<ScrobblerManga>,
	stateHolderListener: ListStateHolderListener,
) : BaseListAdapter<ListModel>() {

	init {
		addDelegate(ListItemType.STATE_LOADING, loadingStateAD())
		addDelegate(ListItemType.MANGA_SCROBBLING, scrobblingMangaAD(clickListener))
		addDelegate(ListItemType.FOOTER_LOADING, loadingFooterAD())
		addDelegate(ListItemType.HINT_EMPTY, scrobblerHintAD(stateHolderListener))
	}
}
