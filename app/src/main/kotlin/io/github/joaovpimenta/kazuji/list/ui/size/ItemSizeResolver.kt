package io.github.joaovpimenta.kazuji.list.ui.size

import android.view.View
import android.widget.TextView
import io.github.joaovpimenta.kazuji.history.ui.util.ReadingProgressView

interface ItemSizeResolver {

	val cellWidth: Int

	fun attachToView(
		view: View,
		textView: TextView?,
		progressView: ReadingProgressView?,
	)
}
