package io.github.kazuji.download.ui.list

import androidx.lifecycle.LifecycleOwner
import io.github.kazuji.core.ui.BaseListAdapter
import io.github.kazuji.list.ui.adapter.ListItemType
import io.github.kazuji.list.ui.adapter.emptyStateListAD
import io.github.kazuji.list.ui.adapter.listHeaderAD
import io.github.kazuji.list.ui.adapter.loadingStateAD
import io.github.kazuji.list.ui.model.ListModel

class DownloadsAdapter(
	lifecycleOwner: LifecycleOwner,
	listener: DownloadItemListener,
) : BaseListAdapter<ListModel>() {

	init {
		addDelegate(ListItemType.DOWNLOAD, downloadItemAD(lifecycleOwner, listener))
		addDelegate(ListItemType.STATE_LOADING, loadingStateAD())
		addDelegate(ListItemType.STATE_EMPTY, emptyStateListAD(null))
		addDelegate(ListItemType.HEADER, listHeaderAD(null))
	}
}
