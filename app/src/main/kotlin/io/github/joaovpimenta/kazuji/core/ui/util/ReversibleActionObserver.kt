package io.github.joaovpimenta.kazuji.core.ui.util

import android.view.View
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.FlowCollector
import io.github.joaovpimenta.kazuji.R
import io.github.joaovpimenta.kazuji.core.util.ext.findActivity
import io.github.joaovpimenta.kazuji.main.ui.owners.BottomNavOwner
import io.github.joaovpimenta.kazuji.main.ui.owners.BottomSheetOwner

class ReversibleActionObserver(
	private val snackbarHost: View,
) : FlowCollector<ReversibleAction> {

	override suspend fun emit(value: ReversibleAction) {
		val handle = value.handle
		val length = if (handle == null) Snackbar.LENGTH_SHORT else Snackbar.LENGTH_LONG
		val snackbar = Snackbar.make(snackbarHost, value.stringResId, length)
		when (val activity = snackbarHost.context.findActivity()) {
			is BottomNavOwner -> snackbar.anchorView = activity.bottomNav
			is BottomSheetOwner -> snackbar.anchorView = activity.bottomSheet
		}
		if (handle != null) {
			snackbar.setAction(R.string.undo) { handle.reverseAsync() }
		}
		snackbar.show()
	}
}
