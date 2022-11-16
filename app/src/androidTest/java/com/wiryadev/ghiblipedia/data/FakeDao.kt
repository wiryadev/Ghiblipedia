package com.wiryadev.ghiblipedia.data

import com.wiryadev.ghiblipedia.data.local.dao.FilmDao
import com.wiryadev.ghiblipedia.data.local.entity.FilmEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeDao : FilmDao {

    private val favoriteFilms = mutableListOf<FilmEntity>()

    override fun getFavoriteFilms(): Flow<List<FilmEntity>> = flowOf(favoriteFilms)

    override fun checkFavorite(filmId: String): Flow<Int> = flowOf(
        favoriteFilms.filter {
            it.id == filmId
        }.size
    )

    override suspend fun addFavoriteFilm(filmEntity: FilmEntity) {
        favoriteFilms.add(filmEntity)
    }

    override suspend fun removeFromFavorite(filmEntity: FilmEntity) {
        favoriteFilms.remove(filmEntity)
    }

}