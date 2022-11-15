package com.wiryadev.ghiblipedia.di

import com.wiryadev.ghiblipedia.ui.screens.favorite.list.FavoriteViewModel
import com.wiryadev.ghiblipedia.ui.screens.films.detail.FilmDetailViewModel
import com.wiryadev.ghiblipedia.ui.screens.films.list.FilmsViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::FilmsViewModel)
    viewModelOf(::FavoriteViewModel)
    viewModelOf(::FilmDetailViewModel)
}