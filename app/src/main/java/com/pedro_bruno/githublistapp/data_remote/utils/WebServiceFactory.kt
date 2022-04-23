package com.pedro_bruno.githublistapp.data_remote.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

object WebServiceFactory {
    inline fun <reified T> createWebService(
        okHttpClient: OkHttpClient,
        url: String = ""
    ): T {
        return Retrofit.Builder()
            .baseUrl(url)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build().create()
    }

    fun providerOkHttClient(): OkHttpClient =
        OkHttpClient.Builder()
            .dispatcher(Dispatcher().apply {
                maxRequests = 1
                maxRequestsPerHost = 1
            })
            .addInterceptor(HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            })
            .connectTimeout(30L, TimeUnit.SECONDS)
            .readTimeout(30L, TimeUnit.SECONDS)
            .writeTimeout(30L, TimeUnit.SECONDS)
            .build()

}