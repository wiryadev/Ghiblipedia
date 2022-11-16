package com.wiryadev.ghiblipedia.data

import com.wiryadev.ghiblipedia.model.Film
import kotlinx.coroutines.flow.Flow

interface GhibliRepository {

    fun getFilms(): Flow<List<Film>>

    fun getFilmDetail(filmId: String): Flow<Film>

    fun getFavoriteFilms(): Flow<List<Film>>

    fun checkFavorite(filmId: String): Flow<Int>

    suspend fun addFavoriteFilm(film: Film)

    suspend fun removeFromFavorite(film: Film)
}