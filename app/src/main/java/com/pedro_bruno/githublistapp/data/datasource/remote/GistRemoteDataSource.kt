package com.pedro_bruno.githublistapp.data.datasource.remote

import com.pedro_bruno.githublistapp.domain.model.Gist
import kotlinx.coroutines.flow.Flow

interface GistRemoteDataSource {
    fun fetchGistList(page: Int): Flow<List<Gist>>

    fun searchGistList(page: Int, owner: String): Flow<List<Gist>>
}