package io.github.kazuji.list.ui.adapter

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegate
import io.github.kazuji.R
import io.github.kazuji.list.ui.model.ListModel
import io.github.kazuji.list.ui.model.LoadingState

fun loadingStateAD() = adapterDelegate<LoadingState, ListModel>(R.layout.item_loading_state) {
}