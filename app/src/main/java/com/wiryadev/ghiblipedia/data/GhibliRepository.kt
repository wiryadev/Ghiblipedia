package com.wiryadev.ghiblipedia.data

import com.wiryadev.ghiblipedia.model.Film

interface GhibliRepository {

    suspend fun getFilms(): Result<List<Film>>

    suspend fun getFilmDetail(filmId: String): Result<Film>
}