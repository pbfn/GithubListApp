package com.pedro_bruno.githublistapp.di

import com.pedro_bruno.githublistapp.domain.usecase.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val domainModule = module {
    single {
        CoroutineScope(Dispatchers.IO)
    }
    factory { FetchGistListRemoteUseCase(get(), get()) }
    factory { FavoriteGistUseCase(get(), get()) }
    factory { RemoveGistFromFavoriteUseCase(get(), get()) }
    factory { FetchGistListLocalUseCase(get(), get()) }
    factory { SearchGistListRemoteUseCase(get(), get()) }
}