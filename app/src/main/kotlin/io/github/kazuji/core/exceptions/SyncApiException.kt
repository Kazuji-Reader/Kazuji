package io.github.kazuji.core.exceptions

class SyncApiException(
	message: String,
	val code: Int,
) : RuntimeException(message)
