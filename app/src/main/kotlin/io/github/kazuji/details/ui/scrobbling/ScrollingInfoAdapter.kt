package io.github.kazuji.details.ui.scrobbling

import io.github.kazuji.core.nav.AppRouter
import io.github.kazuji.core.ui.BaseListAdapter
import io.github.kazuji.list.ui.model.ListModel

class ScrollingInfoAdapter(
	router: AppRouter,
) : BaseListAdapter<ListModel>() {

	init {
		delegatesManager.addDelegate(scrobblingInfoAD(router))
	}
}
