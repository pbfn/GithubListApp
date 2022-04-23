package com.pedro_bruno.githublistapp.di

import androidx.room.Room
import com.pedro_bruno.githublistapp.data_local.database.GistDatabase
import com.pedro_bruno.githublistapp.data_local.utils.LocalConstants.GIST_DATABASE_NAME
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


val databaseModule = module {

    single {
        Room.databaseBuilder(
            androidContext(),
            GistDatabase::class.java,
            GIST_DATABASE_NAME
        ).build()
    }

    single { get<GistDatabase>().gistDao() }
}