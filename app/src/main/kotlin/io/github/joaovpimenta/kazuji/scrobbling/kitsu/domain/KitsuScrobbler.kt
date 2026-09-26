package io.github.joaovpimenta.kazuji.scrobbling.kitsu.domain

import io.github.joaovpimenta.kazuji.core.db.MangaDatabase
import io.github.joaovpimenta.kazuji.core.parser.MangaRepository
import io.github.joaovpimenta.kazuji.scrobbling.common.domain.Scrobbler
import io.github.joaovpimenta.kazuji.scrobbling.common.domain.model.ScrobblerService
import io.github.joaovpimenta.kazuji.scrobbling.common.domain.model.ScrobblingStatus
import io.github.joaovpimenta.kazuji.scrobbling.kitsu.data.KitsuRepository
import javax.inject.Inject

class KitsuScrobbler @Inject constructor(
	private val repository: KitsuRepository,
	db: MangaDatabase,
	mangaRepositoryFactory: MangaRepository.Factory,
) : Scrobbler(db, ScrobblerService.KITSU, repository, mangaRepositoryFactory) {

	init {
		statuses[ScrobblingStatus.PLANNED] = "planned"
		statuses[ScrobblingStatus.READING] = "current"
		statuses[ScrobblingStatus.COMPLETED] = "completed"
		statuses[ScrobblingStatus.ON_HOLD] = "on_hold"
		statuses[ScrobblingStatus.DROPPED] = "dropped"
	}

	override suspend fun updateScrobblingInfo(
		mangaId: Long,
		rating: Float,
		status: ScrobblingStatus?,
		comment: String?
	) {
		val entity = db.getScrobblingDao().find(scrobblerService.id, mangaId)
		requireNotNull(entity) { "Scrobbling info for manga $mangaId not found" }
		repository.updateRate(
			rateId = entity.id,
			mangaId = entity.mangaId,
			rating = rating,
			status = statuses[status],
			comment = comment,
		)
	}

}
