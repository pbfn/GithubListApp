package com.pedro_bruno.githublistapp.data.datasource.local

import com.pedro_bruno.githublistapp.domain.model.Gist
import kotlinx.coroutines.flow.Flow

interface GistLocalDataSource {

    fun favoriteGist(gist: Gist)

    fun removeGistFromFavorites(gist: Gist)

    fun fetchGistList(): Flow<List<Gist>>
}