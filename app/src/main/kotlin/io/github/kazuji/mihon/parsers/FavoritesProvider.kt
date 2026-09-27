package io.github.kazuji.mihon.parsers

import io.github.kazuji.mihon.parsers.model.Content

interface FavoritesProvider {

    suspend fun fetchFavorites(): List<Content>
}
