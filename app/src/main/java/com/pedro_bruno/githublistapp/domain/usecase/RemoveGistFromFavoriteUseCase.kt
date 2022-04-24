package com.pedro_bruno.githublistapp.domain.usecase

import com.pedro_bruno.githublistapp.domain.model.Gist
import com.pedro_bruno.githublistapp.domain.repositories.GistRepository
import com.pedro_bruno.githublistapp.domain.usecase.util.UseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class RemoveGistFromFavoriteUseCase(
    private val gistRepository: GistRepository,
    scope: CoroutineScope
) : UseCase<RemoveGistFromFavoriteUseCase.Params, Unit>(scope = scope) {
    data class Params(
        val gist: Gist
    )

    override fun run(params: Params): Flow<Unit> =
        flowOf(gistRepository.removeGistFromFavorites(params.gist))
}