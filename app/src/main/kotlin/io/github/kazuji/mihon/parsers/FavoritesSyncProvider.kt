package io.github.kazuji.mihon.parsers

import io.github.kazuji.mihon.parsers.model.Content

interface FavoritesSyncProvider {

    suspend fun addFavorite(manga: Content): Boolean

    suspend fun removeFavorite(manga: Content): Boolean
}
