package io.github.joaovpimenta.kazuji.core.exceptions

class SyncApiException(
	message: String,
	val code: Int,
) : RuntimeException(message)
