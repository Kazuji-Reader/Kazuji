package io.github.kazuji.scrobbling

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.ElementsIntoSet
import okhttp3.OkHttpClient
import io.github.kazuji.core.db.MangaDatabase
import io.github.kazuji.core.network.BaseHttpClient
import io.github.kazuji.scrobbling.anilist.data.AniListAuthenticator
import io.github.kazuji.scrobbling.anilist.data.AniListInterceptor
import io.github.kazuji.scrobbling.anilist.domain.AniListScrobbler
import io.github.kazuji.scrobbling.common.data.ScrobblerStorage
import io.github.kazuji.scrobbling.common.domain.Scrobbler
import io.github.kazuji.scrobbling.common.domain.model.ScrobblerService
import io.github.kazuji.scrobbling.common.domain.model.ScrobblerType
import io.github.kazuji.scrobbling.kitsu.data.KitsuAuthenticator
import io.github.kazuji.scrobbling.kitsu.data.KitsuInterceptor
import io.github.kazuji.scrobbling.kitsu.data.KitsuRepository
import io.github.kazuji.scrobbling.kitsu.domain.KitsuScrobbler
import io.github.kazuji.scrobbling.mal.data.MALAuthenticator
import io.github.kazuji.scrobbling.mal.data.MALInterceptor
import io.github.kazuji.scrobbling.mal.domain.MALScrobbler
import io.github.kazuji.scrobbling.shikimori.data.ShikimoriAuthenticator
import io.github.kazuji.scrobbling.shikimori.data.ShikimoriInterceptor
import io.github.kazuji.scrobbling.shikimori.domain.ShikimoriScrobbler
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ScrobblingModule {

	@Provides
	@Singleton
	@ScrobblerType(ScrobblerService.SHIKIMORI)
	fun provideShikimoriHttpClient(
		@BaseHttpClient baseHttpClient: OkHttpClient,
		authenticator: ShikimoriAuthenticator,
		@ScrobblerType(ScrobblerService.SHIKIMORI) storage: ScrobblerStorage,
	): OkHttpClient = baseHttpClient.newBuilder().apply {
		authenticator(authenticator)
		addInterceptor(ShikimoriInterceptor(storage))
	}.build()

	@Provides
	@Singleton
	@ScrobblerType(ScrobblerService.MAL)
	fun provideMALHttpClient(
		@BaseHttpClient baseHttpClient: OkHttpClient,
		authenticator: MALAuthenticator,
		@ScrobblerType(ScrobblerService.MAL) storage: ScrobblerStorage,
	): OkHttpClient = baseHttpClient.newBuilder().apply {
		authenticator(authenticator)
		addInterceptor(MALInterceptor(storage))
	}.build()

	@Provides
	@Singleton
	@ScrobblerType(ScrobblerService.ANILIST)
	fun provideAniListHttpClient(
		@BaseHttpClient baseHttpClient: OkHttpClient,
		authenticator: AniListAuthenticator,
		@ScrobblerType(ScrobblerService.ANILIST) storage: ScrobblerStorage,
	): OkHttpClient = baseHttpClient.newBuilder().apply {
		authenticator(authenticator)
		addInterceptor(AniListInterceptor(storage))
	}.build()

	@Provides
	@Singleton
	fun provideKitsuRepository(
		@ApplicationContext context: Context,
		@BaseHttpClient baseHttpClient: OkHttpClient,
		@ScrobblerType(ScrobblerService.KITSU) storage: ScrobblerStorage,
		database: MangaDatabase,
		authenticator: KitsuAuthenticator,
	): KitsuRepository {
		val okHttp = baseHttpClient.newBuilder().apply {
			authenticator(authenticator)
			addInterceptor(KitsuInterceptor(storage))
		}.build()
		return KitsuRepository(context, okHttp, storage, database)
	}

	@Provides
	@Singleton
	@ScrobblerType(ScrobblerService.ANILIST)
	fun provideAniListStorage(
		@ApplicationContext context: Context,
	): ScrobblerStorage = ScrobblerStorage(context, ScrobblerService.ANILIST)

	@Provides
	@Singleton
	@ScrobblerType(ScrobblerService.SHIKIMORI)
	fun provideShikimoriStorage(
		@ApplicationContext context: Context,
	): ScrobblerStorage = ScrobblerStorage(context, ScrobblerService.SHIKIMORI)

	@Provides
	@Singleton
	@ScrobblerType(ScrobblerService.MAL)
	fun provideMALStorage(
		@ApplicationContext context: Context,
	): ScrobblerStorage = ScrobblerStorage(context, ScrobblerService.MAL)

	@Provides
	@Singleton
	@ScrobblerType(ScrobblerService.KITSU)
	fun provideKitsuStorage(
		@ApplicationContext context: Context,
	): ScrobblerStorage = ScrobblerStorage(context, ScrobblerService.KITSU)

	@Provides
	@ElementsIntoSet
	fun provideScrobblers(
		shikimoriScrobbler: ShikimoriScrobbler,
		aniListScrobbler: AniListScrobbler,
		malScrobbler: MALScrobbler,
		kitsuScrobbler: KitsuScrobbler
	): Set<@JvmSuppressWildcards Scrobbler> = setOf(shikimoriScrobbler, aniListScrobbler, malScrobbler, kitsuScrobbler)
}
