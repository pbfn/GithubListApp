package com.pedro_bruno.githublistapp.data_remote.mappers

import com.pedro_bruno.githublistapp.data_remote.model.response.GistResponse
import com.pedro_bruno.githublistapp.domain.model.Gist


fun List<GistResponse>.toDomain(): MutableList<Gist> {
    val gists: MutableList<Gist> = mutableListOf()
    for (gist in this) {
        gists.add(gist.toDomain())
    }
    return gists
}

fun GistResponse.toDomain(): Gist = Gist(
    nameOwner = this.owner.login,
    photoOwen = this.owner.avatar_url,
    gistType = "teste"
)