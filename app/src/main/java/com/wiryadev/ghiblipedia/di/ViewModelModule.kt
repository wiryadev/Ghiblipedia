package com.wiryadev.ghiblipedia.di

import com.wiryadev.ghiblipedia.ui.films.list.FilmsViewModel
import com.wiryadev.ghiblipedia.ui.films.detail.FilmDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { FilmsViewModel(get()) }
    viewModel { FilmDetailViewModel(get()) }
}