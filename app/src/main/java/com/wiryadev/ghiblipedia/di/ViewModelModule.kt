package com.wiryadev.ghiblipedia.di

import com.wiryadev.ghiblipedia.ui.films.FilmsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { FilmsViewModel(get()) }
}