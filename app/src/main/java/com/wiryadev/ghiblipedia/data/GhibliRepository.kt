package com.wiryadev.ghiblipedia.data

import com.wiryadev.ghiblipedia.data.local.entity.FilmEntity
import com.wiryadev.ghiblipedia.model.Film
import kotlinx.coroutines.flow.Flow

interface GhibliRepository {

    fun getFilms(): Flow<List<Film>>

    fun getFilmDetail(filmId: String): Flow<Film>

    fun getFavoriteFilms(): Flow<List<FilmEntity>>

    suspend fun addFavoriteFilm(filmEntity: FilmEntity)

    suspend fun removeFromFavorite(filmEntity: FilmEntity)
}