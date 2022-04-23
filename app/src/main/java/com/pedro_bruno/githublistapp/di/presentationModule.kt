package com.pedro_bruno.githublistapp.di

import com.pedro_bruno.githublistapp.presentation.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel { HomeViewModel(get()) }
}