package io.github.kazuji.core.exceptions

import okio.IOException

class NoDataReceivedException(
	val url: String,
) : IOException("No data has been received from $url")
