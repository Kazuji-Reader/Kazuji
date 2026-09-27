package io.github.kazuji.core.exceptions

import io.github.kazuji.details.ui.pager.EmptyMangaReason
import org.koitharu.kotatsu.parsers.model.Manga

class EmptyMangaException(
    val reason: EmptyMangaReason?,
    val manga: Manga,
    cause: Throwable?
) : IllegalStateException(cause)
