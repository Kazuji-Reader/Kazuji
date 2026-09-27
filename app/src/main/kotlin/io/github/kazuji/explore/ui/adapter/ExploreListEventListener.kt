package io.github.kazuji.explore.ui.adapter

import android.view.View
import io.github.kazuji.list.ui.adapter.ListHeaderClickListener
import io.github.kazuji.list.ui.adapter.ListStateHolderListener

interface ExploreListEventListener : ListStateHolderListener, View.OnClickListener, ListHeaderClickListener
