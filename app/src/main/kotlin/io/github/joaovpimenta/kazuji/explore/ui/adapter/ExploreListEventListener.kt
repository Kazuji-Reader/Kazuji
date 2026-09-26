package io.github.joaovpimenta.kazuji.explore.ui.adapter

import android.view.View
import io.github.joaovpimenta.kazuji.list.ui.adapter.ListHeaderClickListener
import io.github.joaovpimenta.kazuji.list.ui.adapter.ListStateHolderListener

interface ExploreListEventListener : ListStateHolderListener, View.OnClickListener, ListHeaderClickListener
