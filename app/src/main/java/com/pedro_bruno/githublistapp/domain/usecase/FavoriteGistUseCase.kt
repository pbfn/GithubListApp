package com.pedro_bruno.githublistapp.domain.usecase

import com.pedro_bruno.githublistapp.domain.model.Gist
import com.pedro_bruno.githublistapp.domain.repositories.GistRepository
import com.pedro_bruno.githublistapp.domain.usecase.util.UseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FavoriteGistUseCase(
    private val gistRepository: GistRepository,
    val scope: CoroutineScope
) : UseCase<FavoriteGistUseCase.Params, Unit>(scope = scope) {

    data class Params(
        val gist: Gist
    )

    override fun run(params: Params): Flow<Unit> = when {
        else -> {
            flowOf(gistRepository.favoriteGist(gist = params.gist))
        }
    }


}