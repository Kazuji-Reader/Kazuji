package io.github.joaovpimenta.kazuji.mihon.model

import io.github.joaovpimenta.kazuji.mihon.parsers.model.ContentSource

data class ContentSourceInfo(
    val mangaSource: ContentSource,
    val isEnabled: Boolean,
    val isPinned: Boolean,
) : ContentSource by mangaSource
