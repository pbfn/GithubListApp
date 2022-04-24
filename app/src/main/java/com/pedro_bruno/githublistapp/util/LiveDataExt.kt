package com.pedro_bruno.githublistapp.util

import androidx.lifecycle.MutableLiveData

fun <T> MutableLiveData<ViewState<T>>.postSuccess(data: T) {
    postValue(ViewState.Success(data))
}

fun <T> MutableLiveData<ViewState<T>>.postError(error: Throwable) {
    postValue(ViewState.Error(error))
}

fun <T> MutableLiveData<ViewState<T>>.postNeutral() {
    postValue(ViewState.Neutral)
}