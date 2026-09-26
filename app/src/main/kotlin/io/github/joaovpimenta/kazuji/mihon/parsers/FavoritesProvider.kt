package io.github.joaovpimenta.kazuji.mihon.parsers

import io.github.joaovpimenta.kazuji.mihon.parsers.model.Content

interface FavoritesProvider {

    suspend fun fetchFavorites(): List<Content>
}
