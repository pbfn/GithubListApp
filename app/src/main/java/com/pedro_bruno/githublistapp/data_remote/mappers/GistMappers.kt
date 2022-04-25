package com.pedro_bruno.githublistapp.data_remote.mappers

import com.pedro_bruno.githublistapp.data_remote.model.response.GistResponse
import com.pedro_bruno.githublistapp.data_remote.model.response.TypeResponse
import com.pedro_bruno.githublistapp.domain.model.Gist
import com.pedro_bruno.githublistapp.domain.model.TypeGist


fun List<GistResponse>.toDomain(): MutableList<Gist> {
    val gists: MutableList<Gist> = mutableListOf()
    for (gist in this) {
        gists.add(gist.toDomain())
    }
    return gists
}

fun GistResponse.toDomain(): Gist {
    val typesGist: MutableList<TypeGist> = mutableListOf()
    for (file in this.files) {
        typesGist.add(file.value.toDomain())
    }
    return Gist(
        id = this.id,
        nameOwner = this.owner.login,
        photoOwen = this.owner.avatar_url,
        gistType = typesGist
    )

}

fun TypeResponse.toDomain(): TypeGist = TypeGist(
    filename = this.filename,
    type = this.type
)

