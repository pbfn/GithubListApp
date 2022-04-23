package com.pedro_bruno.githublistapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pedro_bruno.githublistapp.domain.model.Gist
import com.pedro_bruno.githublistapp.domain.usecase.FetchGistListUseCase
import com.pedro_bruno.githublistapp.util.ViewState
import com.pedro_bruno.githublistapp.util.postError
import com.pedro_bruno.githublistapp.util.postSuccess

class HomeViewModel(
    private val fetchGistListUseCase: FetchGistListUseCase
) : ViewModel() {

    private val _gistList = MutableLiveData<ViewState<List<Gist>>>()
    val gistList: LiveData<ViewState<List<Gist>>> = _gistList



     fun fetchGistList() {
        fetchGistListUseCase(
            params = Unit,
            onSuccess = {
                _gistList.postSuccess(it)
            },
            onError = {
                _gistList.postError(it)
            }
        )
    }

}