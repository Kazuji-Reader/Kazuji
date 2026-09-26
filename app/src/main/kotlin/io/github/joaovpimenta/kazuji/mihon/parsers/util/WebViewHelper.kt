package io.github.joaovpimenta.kazuji.mihon.parsers.util

import io.github.joaovpimenta.kazuji.mihon.parsers.ContentLoaderContext

public class WebViewHelper(
	private val context: ContentLoaderContext,
) {

	public suspend fun getLocalStorageValue(domain: String, key: String): String? {
		return context.evaluateJs("$SCHEME_HTTPS://$domain/", "window.localStorage.getItem(\"$key\")")
	}
}

