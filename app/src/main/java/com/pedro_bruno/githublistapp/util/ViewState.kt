package com.pedro_bruno.githublistapp.util

import androidx.lifecycle.MutableLiveData

sealed class ViewState<out T> {

    data class Success<T>(
        val data: T
    ) : ViewState<T>()

    data class Error(
        val throwable: Throwable
    ) : ViewState<Nothing>()

    object Neutral : ViewState<Nothing>()

    fun <T> MutableLiveData<ViewState<T>>.postNeutral() {
        postValue(ViewState.Neutral)
    }
}