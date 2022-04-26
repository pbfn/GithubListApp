package com.pedro_bruno.githublistapp.domain.usecase

import com.pedro_bruno.githublistapp.domain.model.Gist
import com.pedro_bruno.githublistapp.domain.repositories.GistRepository
import com.pedro_bruno.githublistapp.domain.usecase.util.UseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

class SearchGistListRemoteUseCase(
    scope: CoroutineScope,
    private val gistRepository: GistRepository
) : UseCase<SearchGistListRemoteUseCase.Params, List<Gist>>(scope = scope) {

    data class Params(
        val page: Int,
        val owner: String
    )

    override fun run(params: Params): Flow<List<Gist>> = when {
        else -> {
            gistRepository.searchGistList(page = params.page, owner = params.owner)
        }
    }
}