package io.github.joaovpimenta.kazuji.browser.cloudflare

import io.github.joaovpimenta.kazuji.browser.BrowserCallback

interface CloudFlareCallback : BrowserCallback {

	override fun onTitleChanged(title: CharSequence, subtitle: CharSequence?) = Unit

	fun onPageLoaded()

	fun onCheckPassed()

	fun onLoopDetected()
}
