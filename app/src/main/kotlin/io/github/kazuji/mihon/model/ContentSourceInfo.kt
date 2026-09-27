package io.github.kazuji.mihon.model

import io.github.kazuji.mihon.parsers.model.ContentSource

data class ContentSourceInfo(
    val mangaSource: ContentSource,
    val isEnabled: Boolean,
    val isPinned: Boolean,
) : ContentSource by mangaSource
