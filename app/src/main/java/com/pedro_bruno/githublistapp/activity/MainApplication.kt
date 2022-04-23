package com.pedro_bruno.githublistapp.activity

import android.app.Application
import com.pedro_bruno.githublistapp.di.dataModule
import com.pedro_bruno.githublistapp.di.dataRemoteModule
import com.pedro_bruno.githublistapp.di.domainModule
import com.pedro_bruno.githublistapp.di.presentationModule
import org.koin.core.context.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(
                dataModule,
                dataRemoteModule,
                domainModule,
                presentationModule
            )
        }
    }
}