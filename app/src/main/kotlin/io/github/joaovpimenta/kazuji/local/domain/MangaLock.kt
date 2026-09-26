package io.github.joaovpimenta.kazuji.local.domain

import io.github.joaovpimenta.kazuji.core.util.MultiMutex
import org.koitharu.kotatsu.parsers.model.Manga
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MangaLock @Inject constructor() : MultiMutex<Manga>()
