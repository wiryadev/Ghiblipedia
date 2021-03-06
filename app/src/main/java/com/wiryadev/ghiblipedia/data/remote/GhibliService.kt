package com.wiryadev.ghiblipedia.data.remote

import com.wiryadev.ghiblipedia.model.Film
import retrofit2.http.GET
import retrofit2.http.Path

interface GhibliService {

    @GET("films")
    suspend fun getFilms(): List<Film>

    @GET("films/{id}")
    suspend fun getFilmDetail(@Path("id") filmId: String): Film
}