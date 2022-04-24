package com.pedro_bruno.githublistapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pedro_bruno.githublistapp.domain.model.Gist
import com.pedro_bruno.githublistapp.domain.usecase.FavoriteGistUseCase
import com.pedro_bruno.githublistapp.domain.usecase.FetchGistListLocalUseCase
import com.pedro_bruno.githublistapp.domain.usecase.FetchGistListRemoteUseCase
import com.pedro_bruno.githublistapp.domain.usecase.RemoveGistFromFavoriteUseCase
import com.pedro_bruno.githublistapp.util.ViewState
import com.pedro_bruno.githublistapp.util.postError
import com.pedro_bruno.githublistapp.util.postNeutral
import com.pedro_bruno.githublistapp.util.postSuccess

class HomeViewModel(
    private val fetchGistListRemoteUseCase: FetchGistListRemoteUseCase,
    private val favoriteGistUseCase: FavoriteGistUseCase,
    private val removeGistFromFavoriteUseCase: RemoveGistFromFavoriteUseCase,
) : ViewModel() {

    private val _gistList = MutableLiveData<ViewState<List<Gist>>>()
    val gistList: LiveData<ViewState<List<Gist>>> = _gistList

    init {
        fetchRemoteGistList()
    }

    private fun fetchRemoteGistList() {
        fetchGistListRemoteUseCase(
            params = Unit,
            onSuccess = {
                _gistList.postSuccess(it)
            },
            onError = {
                _gistList.postError(it)
            }
        )
    }

    fun favoriteGist(gist: Gist) {
        favoriteGistUseCase(
            params = FavoriteGistUseCase.Params(
                gist = gist
            ),
            onSuccess = {

            },
            onError = {

            }
        )
    }

    fun removeGistFromfavorite(gist: Gist) {
        removeGistFromFavoriteUseCase(
            params = RemoveGistFromFavoriteUseCase.Params(
                gist = gist
            )
        )
    }

}