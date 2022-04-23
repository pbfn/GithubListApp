package com.pedro_bruno.githublistapp.data.repositories

import com.pedro_bruno.githublistapp.data.datasource.local.GistLocalDataSource
import com.pedro_bruno.githublistapp.data.datasource.remote.GistRemoteDataSource
import com.pedro_bruno.githublistapp.domain.model.Gist
import com.pedro_bruno.githublistapp.domain.repositories.GistRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class GistRepositoryImpl(
    private val gistRemoteDataSource: GistRemoteDataSource,
    private val gistLocalDataSource: GistLocalDataSource
) : GistRepository {

    override fun fetchGistList(): Flow<List<Gist>> = flow {
        gistRemoteDataSource.fetchGistList().collect { listResponse ->
            emit(listResponse)
        }
    }

    override fun favoriteGist(gist:Gist) {
        gistLocalDataSource.favoriteGist(gist = gist)
    }

    override fun removeGistFromFavorites(gist: Gist) {
        gistLocalDataSource.removeGistFromFavorites(gist = gist)
    }
}