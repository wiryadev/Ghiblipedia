package com.wiryadev.ghiblipedia.data

import com.wiryadev.ghiblipedia.model.Film
import kotlinx.coroutines.flow.Flow

interface GhibliRepository {

    fun getFilms(): Flow<List<Film>>

    fun getFilmDetail(filmId: String): Flow<Film>
}