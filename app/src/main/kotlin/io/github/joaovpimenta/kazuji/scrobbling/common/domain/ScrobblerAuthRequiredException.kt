package io.github.joaovpimenta.kazuji.scrobbling.common.domain

import okio.IOException
import io.github.joaovpimenta.kazuji.scrobbling.common.domain.model.ScrobblerService

class ScrobblerAuthRequiredException(
	val scrobbler: ScrobblerService,
) : IOException()
