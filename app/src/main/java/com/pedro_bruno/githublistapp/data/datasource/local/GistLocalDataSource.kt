package com.pedro_bruno.githublistapp.data.datasource.local

import com.pedro_bruno.githublistapp.domain.model.Gist

interface GistLocalDataSource {

    fun favoriteGist(gist: Gist)

    fun removeGistFromFavorites(gist: Gist)
}