package io.github.kazuji.browser.cloudflare

import io.github.kazuji.browser.BrowserCallback

interface CloudFlareCallback : BrowserCallback {

	override fun onTitleChanged(title: CharSequence, subtitle: CharSequence?) = Unit

	fun onPageLoaded()

	fun onCheckPassed()

	fun onLoopDetected()
}
