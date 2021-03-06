package com.pedro_bruno.githublistapp.data_local.mappers

import com.pedro_bruno.githublistapp.data_local.model.GistDataLocal
import com.pedro_bruno.githublistapp.domain.model.Gist


fun Gist.toDao(): GistDataLocal = GistDataLocal(
    id = this.id,
    nameOwner = this.nameOwner,
    photoOwen = this.photoOwen,
    gistType = this.gistType,
)

fun List<GistDataLocal>.toDomain(): MutableList<Gist> {
    val gists: MutableList<Gist> = mutableListOf()
    for (gist in this) {
        gists.add(gist.toDomain())
    }
    return gists
}

fun GistDataLocal.toDomain(): Gist = Gist(
    id = this.id,
    nameOwner = this.nameOwner,
    photoOwen = this.photoOwen,
    gistType = this.gistType,
    checked = true
)