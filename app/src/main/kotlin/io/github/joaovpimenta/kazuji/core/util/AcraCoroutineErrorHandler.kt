package io.github.joaovpimenta.kazuji.core.util

import io.github.joaovpimenta.kazuji.core.util.ext.printStackTraceDebug
import io.github.joaovpimenta.kazuji.core.util.ext.report
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext

class AcraCoroutineErrorHandler : AbstractCoroutineContextElement(CoroutineExceptionHandler),
	CoroutineExceptionHandler {

	override fun handleException(context: CoroutineContext, exception: Throwable) {
		exception.printStackTraceDebug("AcraCoroutineErrorHandler::handleException")
		exception.report()
	}
}
