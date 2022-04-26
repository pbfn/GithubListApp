package com.pedro_bruno.githublistapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pedro_bruno.githublistapp.domain.model.Gist
import com.pedro_bruno.githublistapp.domain.usecase.FavoriteGistUseCase
import com.pedro_bruno.githublistapp.domain.usecase.FetchGistListRemoteUseCase
import com.pedro_bruno.githublistapp.domain.usecase.RemoveGistFromFavoriteUseCase
import com.pedro_bruno.githublistapp.domain.usecase.SearchGistListRemoteUseCase
import com.pedro_bruno.githublistapp.util.ViewState
import com.pedro_bruno.githublistapp.util.postError
import com.pedro_bruno.githublistapp.util.postNeutral
import com.pedro_bruno.githublistapp.util.postSuccess

class HomeViewModel(
    private val fetchGistListRemoteUseCase: FetchGistListRemoteUseCase,
    private val favoriteGistUseCase: FavoriteGistUseCase,
    private val removeGistFromFavoriteUseCase: RemoveGistFromFavoriteUseCase,
    private val searchGistListRemoteUseCase: SearchGistListRemoteUseCase
) : ViewModel() {

    private val _gistList = MutableLiveData<ViewState<List<Gist>>>()
    val gistList: LiveData<ViewState<List<Gist>>> = _gistList

    private var _showProgressBar = MutableLiveData<Boolean>()
    var showProgressBar: LiveData<Boolean> = _showProgressBar

    private var pageGist: Int = 0
    private var searchPageGist: Int = 0

    init {
        fetchRemoteGistList()
    }

    fun fetchRemoteGistList(oldGistList: MutableList<Gist> = mutableListOf()) {
        _showProgressBar.postValue(true)
        fetchGistListRemoteUseCase(
            params = FetchGistListRemoteUseCase.Params(
                page = pageGist
            ),
            onSuccess = { response ->
                pageGist++
                searchPageGist = 0
                if (oldGistList.isNullOrEmpty()) {
                    _showProgressBar.postValue(false)
                    _gistList.postSuccess(response)
                } else {
                    val listResponse: MutableList<Gist> = mutableListOf()
                    listResponse.addAll(oldGistList)
                    listResponse.addAll(response)
                    _showProgressBar.postValue(false)
                    _gistList.postSuccess(listResponse)
                }
            },
            onError = {
                _showProgressBar.postValue(false)
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

    fun removeGistFavorite(gist: Gist) {
        removeGistFromFavoriteUseCase(
            params = RemoveGistFromFavoriteUseCase.Params(
                gist = gist
            )
        )
    }

    fun searchGistList(oldGistList: MutableList<Gist> = mutableListOf(), owner: String) {
        _showProgressBar.postValue(true)
        searchGistListRemoteUseCase(
            params = SearchGistListRemoteUseCase.Params(
                page = searchPageGist,
                owner = owner
            ),
            onSuccess = { response ->
                pageGist = 0
                searchPageGist++
                if (oldGistList.isNullOrEmpty()) {
                    _showProgressBar.postValue(false)
                    _gistList.postSuccess(response)
                } else {
                    val listResponse: MutableList<Gist> = mutableListOf()
                    listResponse.addAll(oldGistList)
                    listResponse.addAll(response)
                    _showProgressBar.postValue(false)
                    _gistList.postSuccess(listResponse)
                }
            },
            onError = {
                _showProgressBar.postValue(false)
                _gistList.postError(it)
            }
        )
    }
}