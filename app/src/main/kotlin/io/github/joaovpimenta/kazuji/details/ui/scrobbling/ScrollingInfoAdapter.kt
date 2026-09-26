package io.github.joaovpimenta.kazuji.details.ui.scrobbling

import io.github.joaovpimenta.kazuji.core.nav.AppRouter
import io.github.joaovpimenta.kazuji.core.ui.BaseListAdapter
import io.github.joaovpimenta.kazuji.list.ui.model.ListModel

class ScrollingInfoAdapter(
	router: AppRouter,
) : BaseListAdapter<ListModel>() {

	init {
		delegatesManager.addDelegate(scrobblingInfoAD(router))
	}
}
