package com.pedro_bruno.githublistapp.domain.repositories

import com.pedro_bruno.githublistapp.domain.model.Gist
import kotlinx.coroutines.flow.Flow

interface GistRepository {

    fun fetchGistList(): Flow<List<Gist>>

    fun favoriteGist(gist: Gist)

    fun removeGistFromFavorites(gist: Gist)
}