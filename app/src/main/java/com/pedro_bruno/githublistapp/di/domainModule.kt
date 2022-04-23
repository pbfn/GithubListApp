package com.pedro_bruno.githublistapp.di

import com.pedro_bruno.githublistapp.domain.usecase.FetchGistListUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val domainModule = module {
    single {
        CoroutineScope(Dispatchers.IO)
    }
    factory { FetchGistListUseCase(get(), get()) }
}