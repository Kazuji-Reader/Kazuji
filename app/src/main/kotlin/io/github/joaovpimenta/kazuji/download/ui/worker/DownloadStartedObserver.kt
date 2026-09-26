package io.github.joaovpimenta.kazuji.download.ui.worker

import android.view.View
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.FlowCollector
import io.github.joaovpimenta.kazuji.R
import io.github.joaovpimenta.kazuji.core.nav.AppRouter
import io.github.joaovpimenta.kazuji.core.util.ext.findActivity
import io.github.joaovpimenta.kazuji.main.ui.owners.BottomNavOwner

class DownloadStartedObserver(
	private val snackbarHost: View,
) : FlowCollector<Unit> {

	override suspend fun emit(value: Unit) {
		val snackbar = Snackbar.make(snackbarHost, R.string.download_started, Snackbar.LENGTH_LONG)
		(snackbarHost.context.findActivity() as? BottomNavOwner)?.let {
			snackbar.anchorView = it.bottomNav
		}
		val router = AppRouter.from(snackbarHost)
		if (router != null) {
			snackbar.setAction(R.string.details) { router.openDownloads() }
		}
		snackbar.show()
	}
}
