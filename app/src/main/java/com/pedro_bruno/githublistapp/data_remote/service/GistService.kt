package com.pedro_bruno.githublistapp.data_remote.service

import com.pedro_bruno.githublistapp.data_remote.model.response.GistResponse
import com.pedro_bruno.githublistapp.data_remote.utils.ApiConstants
import retrofit2.Response
import retrofit2.http.*

interface GistService {

    @Headers("Content-type: application/json")
    @GET("gists/public")
    suspend fun fetchGistList(
        @Query("page")
        page: Int
    ): Response<List<GistResponse>>

    @Headers("Content-type: application/json")
    @GET("users/{username}/gists")
    suspend fun searchGistList(
        @Path("username")
        username: String,
        @Query("page")
        page: Int
    ): Response<List<GistResponse>>


}