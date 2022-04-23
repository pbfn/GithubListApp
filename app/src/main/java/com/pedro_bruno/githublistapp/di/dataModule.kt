package com.pedro_bruno.githublistapp.di

import com.pedro_bruno.githublistapp.data.repositories.GistRepositoryImpl
import com.pedro_bruno.githublistapp.domain.repositories.GistRepository
import org.koin.dsl.module


val dataModule = module {
    single<GistRepository> {
        GistRepositoryImpl(get(), get())
    }
}