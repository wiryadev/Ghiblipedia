package com.wiryadev.ghiblipedia.data.remote

import com.wiryadev.ghiblipedia.data.remote.model.FilmDto
import com.wiryadev.ghiblipedia.data.remote.retrofit.GhibliService

class RemoteDataSource constructor(private val service: GhibliService) {

    suspend fun getFilms(): List<FilmDto> = service.getFilms()

    suspend fun getFilmDetail(filmId: String): FilmDto = service.getFilmDetail(filmId)
}