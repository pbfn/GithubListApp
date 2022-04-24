package com.pedro_bruno.githublistapp.data_remote.datasource

import com.pedro_bruno.githublistapp.data.datasource.remote.GistRemoteDataSource
import com.pedro_bruno.githublistapp.data_remote.mappers.toDomain
import com.pedro_bruno.githublistapp.data_remote.service.GistService
import com.pedro_bruno.githublistapp.domain.exceptions.LimitResquestException
import com.pedro_bruno.githublistapp.domain.model.Gist
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GistRemoteDataSourceImpl(
    private val gistService: GistService
) : GistRemoteDataSource {
    override fun fetchGistList(): Flow<List<Gist>> = flow {
        val response = gistService.fetchGistList()

        if (response.isSuccessful) {
            response.body()?.let { listGistReponse ->
                emit(listGistReponse.toDomain())
            }
        } else {
            when (response.code()) {
                403 -> {
                    emit(throw LimitResquestException())
                }
            }
        }
    }
}