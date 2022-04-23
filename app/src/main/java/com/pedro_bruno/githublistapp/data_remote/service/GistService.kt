package com.pedro_bruno.githublistapp.data_remote.service

import com.pedro_bruno.githublistapp.data_remote.model.response.GistResponse
import com.pedro_bruno.githublistapp.data_remote.utils.ApiConstants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface GistService {

    @Headers("Content-type: application/json")
    @GET("gists")
    suspend fun fetchGistList(
    ): Response<List<GistResponse>>
}