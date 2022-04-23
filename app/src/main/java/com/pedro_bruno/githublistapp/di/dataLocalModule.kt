package com.pedro_bruno.githublistapp.di

import com.pedro_bruno.githublistapp.data.datasource.local.GistLocalDataSource
import com.pedro_bruno.githublistapp.data_local.datasource.GistLocalDataSourceImpl
import org.koin.dsl.module

val dataLocalModule = module {
    single<GistLocalDataSource> { GistLocalDataSourceImpl(get()) }
}