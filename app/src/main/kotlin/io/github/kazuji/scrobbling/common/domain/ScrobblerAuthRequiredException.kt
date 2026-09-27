package io.github.kazuji.scrobbling.common.domain

import okio.IOException
import io.github.kazuji.scrobbling.common.domain.model.ScrobblerService

class ScrobblerAuthRequiredException(
	val scrobbler: ScrobblerService,
) : IOException()
