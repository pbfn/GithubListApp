package com.pedro_bruno.githublistapp.di

import com.pedro_bruno.githublistapp.data.datasource.remote.GistRemoteDataSource
import com.pedro_bruno.githublistapp.data_remote.datasource.GistRemoteDataSourceImpl
import com.pedro_bruno.githublistapp.data_remote.service.GistService
import com.pedro_bruno.githublistapp.data_remote.utils.ApiConstants.BASE_URL
import com.pedro_bruno.githublistapp.data_remote.utils.WebServiceFactory
import org.koin.dsl.module

val dataRemoteModule = module {

    single<GistRemoteDataSource> {
        GistRemoteDataSourceImpl(get())
    }

    single<GistService> {
        WebServiceFactory.createWebService(
            okHttpClient = get(),
            url = BASE_URL
        )
    }

    single { WebServiceFactory.providerOkHttClient() }
}