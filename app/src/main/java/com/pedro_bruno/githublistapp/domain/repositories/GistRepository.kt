package com.pedro_bruno.githublistapp.domain.repositories

import com.pedro_bruno.githublistapp.domain.model.Gist
import kotlinx.coroutines.flow.Flow

interface GistRepository {

    fun fetchGistListRemote(page: Int): Flow<List<Gist>>

    fun favoriteGist(gist: Gist)

    fun removeGistFromFavorites(gist: Gist)

    fun fetchGistListLocal(): Flow<List<Gist>>

    fun searchGistList(page: Int,owner: String): Flow<List<Gist>>
}