package com.pedro_bruno.githublistapp.domain.usecase

import com.pedro_bruno.githublistapp.domain.model.Gist
import com.pedro_bruno.githublistapp.domain.repositories.GistRepository
import com.pedro_bruno.githublistapp.domain.usecase.util.UseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

class FetchGistListLocalUseCase(
    scope: CoroutineScope,
    private val gistRepository: GistRepository
) : UseCase<Unit, List<Gist>>(scope = scope) {

    override fun run(params: Unit): Flow<List<Gist>> {
        return gistRepository.fetchGistListLocal()
    }
}