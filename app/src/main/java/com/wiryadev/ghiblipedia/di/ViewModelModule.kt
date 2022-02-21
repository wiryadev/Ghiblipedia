package com.wiryadev.ghiblipedia.di

import com.wiryadev.ghiblipedia.ui.films.detail.FilmDetailViewModel
import com.wiryadev.ghiblipedia.ui.films.list.FilmsViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::FilmsViewModel)
    viewModelOf(::FilmDetailViewModel)
}