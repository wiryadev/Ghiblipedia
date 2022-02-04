package com.wiryadev.ghiblipedia.data.remote

import com.wiryadev.ghiblipedia.model.Film

class RemoteDataSource constructor(private val service: GhibliService) {

    suspend fun getFilms(): List<Film> = service.getFilms()

    suspend fun getFilmDetail(filmId: String): Film = service.getFilmDetail(filmId)
}