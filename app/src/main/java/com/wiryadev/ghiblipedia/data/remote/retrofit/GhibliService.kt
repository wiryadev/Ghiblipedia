package com.wiryadev.ghiblipedia.data.remote.retrofit

import com.wiryadev.ghiblipedia.data.remote.model.FilmDto
import com.wiryadev.ghiblipedia.model.Film
import retrofit2.http.GET
import retrofit2.http.Path

interface GhibliService {

    @GET("films")
    suspend fun getFilms(): List<FilmDto>

    @GET("films/{id}")
    suspend fun getFilmDetail(@Path("id") filmId: String): FilmDto
}