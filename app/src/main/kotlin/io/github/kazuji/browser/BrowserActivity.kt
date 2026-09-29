package io.github.kazuji.browser

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.webkit.CookieManager
import androidx.activity.result.contract.ActivityResultContract
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koitharu.kotatsu.parsers.model.MangaSource
import io.github.kazuji.R
import io.github.kazuji.core.exceptions.InteractiveActionRequiredException
import io.github.kazuji.core.nav.AppRouter
import io.github.kazuji.core.nav.router
import io.github.kazuji.core.parser.ParserMangaRepository
import io.github.kazuji.core.util.ext.getDisplayMessage
import io.github.kazuji.core.util.ext.printStackTraceDebug

@AndroidEntryPoint
class BrowserActivity : BaseBrowserActivity() {
	private var isCompletingInteractiveAction = false

	override fun onCreate2(savedInstanceState: Bundle?, source: MangaSource, repository: ParserMangaRepository?) {
		setDisplayHomeAsUp(isEnabled = true, showUpAsClose = true)
		viewBinding.webView.webViewClient = BrowserClient(this, adBlock)
		if (intent?.getBooleanExtra(EXTRA_INTERACTIVE_ACTION, false) == true) {
			Snackbar.make(viewBinding.webView, R.string.browser_action_complete_hint, Snackbar.LENGTH_LONG).show()
		}
		lifecycleScope.launch {
			try {
				proxyProvider.applyWebViewConfig()
			} catch (e: Exception) {
				e.printStackTraceDebug("BrowserActivity::onCreate2")
				Snackbar.make(viewBinding.webView, e.getDisplayMessage(resources), Snackbar.LENGTH_LONG).show()
			}
			if (savedInstanceState == null) {
				val url = intent?.dataString
				if (url.isNullOrEmpty()) {
					finishAfterTransition()
				} else {
					onTitleChanged(
						intent?.getStringExtra(AppRouter.KEY_TITLE) ?: getString(R.string.loading_),
						url,
					)
					viewBinding.webView.loadUrl(url)
				}
			}
		}
	}

	override fun onCreateOptionsMenu(menu: Menu): Boolean {
		super.onCreateOptionsMenu(menu)
		menuInflater.inflate(R.menu.opt_browser, menu)
		menu.findItem(R.id.action_browser_complete)?.isVisible = isInteractiveAction
		return true
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
		android.R.id.home -> {
			viewBinding.webView.stopLoading()
			finishAfterTransition()
			true
		}

		R.id.action_browser -> {
			if (!router.openExternalBrowser(viewBinding.webView.url.orEmpty(), item.title)) {
				Snackbar.make(viewBinding.webView, R.string.operation_not_supported, Snackbar.LENGTH_SHORT).show()
			}
			true
		}

		R.id.action_browser_complete -> {
			completeInteractiveAction()
			true
		}

		else -> super.onOptionsItemSelected(item)
	}

	private val isInteractiveAction: Boolean
		get() = intent?.getBooleanExtra(EXTRA_INTERACTIVE_ACTION, false) == true

	private fun completeInteractiveAction() {
		if (isCompletingInteractiveAction) return
		isCompletingInteractiveAction = true
		lifecycleScope.launch {
			try {
				withContext(Dispatchers.IO) {
					CookieManager.getInstance().flush()
				}
			} catch (e: CancellationException) {
				throw e
			} catch (e: Exception) {
				e.printStackTraceDebug("BrowserActivity::completeInteractiveAction")
			}
			setResult(Activity.RESULT_OK)
			finishAfterTransition()
		}
	}

	class Contract : ActivityResultContract<InteractiveActionRequiredException, Boolean>() {
		override fun createIntent(
			context: Context,
			input: InteractiveActionRequiredException
		): Intent = AppRouter.browserIntent(
			context = context,
			url = input.url,
			source = input.source,
			title = null,
		).putExtra(EXTRA_INTERACTIVE_ACTION, true)

		override fun parseResult(resultCode: Int, intent: Intent?): Boolean = resultCode == Activity.RESULT_OK
	}

	companion object {

		const val TAG = "BrowserActivity"
		private const val EXTRA_INTERACTIVE_ACTION = "io.github.kazuji.browser.INTERACTIVE_ACTION"
	}
}
