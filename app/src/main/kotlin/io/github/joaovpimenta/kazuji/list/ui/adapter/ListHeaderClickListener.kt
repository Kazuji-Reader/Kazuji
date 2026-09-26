package io.github.joaovpimenta.kazuji.list.ui.adapter

import android.view.View
import io.github.joaovpimenta.kazuji.list.ui.model.ListHeader

interface ListHeaderClickListener {

	fun onListHeaderClick(item: ListHeader, view: View)
}
