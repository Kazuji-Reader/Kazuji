package io.github.joaovpimenta.kazuji.core.exceptions

import okio.IOException

class WrapperIOException(override val cause: Exception) : IOException(cause)
