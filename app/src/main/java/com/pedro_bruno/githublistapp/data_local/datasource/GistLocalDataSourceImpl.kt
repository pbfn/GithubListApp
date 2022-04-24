package com.pedro_bruno.githublistapp.data_local.datasource

import com.pedro_bruno.githublistapp.data.datasource.local.GistLocalDataSource
import com.pedro_bruno.githublistapp.data_local.database.GistDao
import com.pedro_bruno.githublistapp.data_local.mappers.toDao
import com.pedro_bruno.githublistapp.data_local.mappers.toDomain
import com.pedro_bruno.githublistapp.domain.model.Gist
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GistLocalDataSourceImpl(
    private val gistDao: GistDao
) : GistLocalDataSource {

    override fun favoriteGist(gist: Gist) = gistDao.favoriteGist(gist = gist.toDao())

    override fun removeGistFromFavorites(gist: Gist) = gistDao.removeGist(gist = gist.toDao())

    override fun fetchGistList(): Flow<List<Gist>> = flow {
        emit(gistDao.fetchListFavorites().toDomain())
    }
}