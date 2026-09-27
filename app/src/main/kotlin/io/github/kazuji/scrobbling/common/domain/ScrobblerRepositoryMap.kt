package io.github.kazuji.scrobbling.common.domain

import io.github.kazuji.scrobbling.anilist.data.AniListRepository
import io.github.kazuji.scrobbling.common.data.ScrobblerRepository
import io.github.kazuji.scrobbling.common.domain.model.ScrobblerService
import io.github.kazuji.scrobbling.kitsu.data.KitsuRepository
import io.github.kazuji.scrobbling.mal.data.MALRepository
import io.github.kazuji.scrobbling.shikimori.data.ShikimoriRepository
import javax.inject.Inject
import javax.inject.Provider

class ScrobblerRepositoryMap @Inject constructor(
	private val shikimoriRepository: Provider<ShikimoriRepository>,
	private val aniListRepository: Provider<AniListRepository>,
	private val malRepository: Provider<MALRepository>,
	private val kitsuRepository: Provider<KitsuRepository>,
) {

	operator fun get(scrobblerService: ScrobblerService): ScrobblerRepository = when (scrobblerService) {
		ScrobblerService.SHIKIMORI -> shikimoriRepository
		ScrobblerService.ANILIST -> aniListRepository
		ScrobblerService.MAL -> malRepository
		ScrobblerService.KITSU -> kitsuRepository
	}.get()
}
