package com.pedro_bruno.githublistapp.activity

import android.app.Application
import com.pedro_bruno.githublistapp.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(
                databaseModule,
                dataLocalModule,
                dataModule,
                dataRemoteModule,
                domainModule,
                presentationModule
            ).androidContext(applicationContext)
        }
    }
}