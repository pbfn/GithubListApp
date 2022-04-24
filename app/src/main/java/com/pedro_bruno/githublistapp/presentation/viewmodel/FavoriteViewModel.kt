package com.pedro_bruno.githublistapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pedro_bruno.githublistapp.domain.model.Gist
import com.pedro_bruno.githublistapp.domain.usecase.FavoriteGistUseCase
import com.pedro_bruno.githublistapp.domain.usecase.FetchGistListLocalUseCase
import com.pedro_bruno.githublistapp.domain.usecase.RemoveGistFromFavoriteUseCase
import com.pedro_bruno.githublistapp.util.ViewState
import com.pedro_bruno.githublistapp.util.postError
import com.pedro_bruno.githublistapp.util.postSuccess

class FavoriteViewModel(
    private val removeGistFromFavoriteUseCase: RemoveGistFromFavoriteUseCase,
    private val fetchGistListLocalUseCase: FetchGistListLocalUseCase,
    private val favoriteGistUseCase: FavoriteGistUseCase,
) : ViewModel() {

    private val _gistList = MutableLiveData<ViewState<List<Gist>>>()
    val gistList: LiveData<ViewState<List<Gist>>> = _gistList

    private val _removeGistResponse = MutableLiveData<ViewState<Gist>>()
    val removeGistResponse: LiveData<ViewState<Gist>> = _removeGistResponse

    private val _favoriGistResponse = MutableLiveData<ViewState<Gist>>()
    val favoriteGistResponse: LiveData<ViewState<Gist>> = _favoriGistResponse

    fun removeGistFromfavorite(gist: Gist) {
        removeGistFromFavoriteUseCase(
            params = RemoveGistFromFavoriteUseCase.Params(
                gist = gist
            ),
            onSuccess = {
                _removeGistResponse.postSuccess(gist)
            },
            onError = {
                _removeGistResponse.postError(it)
            }
        )
    }

    fun fetchLocalGistList() {
        fetchGistListLocalUseCase(params = Unit,
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
                _favoriGistResponse.postSuccess(gist)
            },
            onError = {

            }
        )
    }
}