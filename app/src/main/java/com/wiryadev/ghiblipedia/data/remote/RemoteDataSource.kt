package com.wiryadev.ghiblipedia.data.remote

class RemoteDataSource constructor(private val service: GhibliService) {

    suspend fun getFilms() = service.getFilms()

    suspend fun getFilmDetail(filmId: String) = service.getFilmDetail(filmId)
}