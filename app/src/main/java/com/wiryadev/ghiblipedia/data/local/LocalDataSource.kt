package com.wiryadev.ghiblipedia.data.local

import com.wiryadev.ghiblipedia.data.local.dao.FilmDao
import com.wiryadev.ghiblipedia.data.local.entity.FilmEntity

class LocalDataSource(private val filmDao: FilmDao) {

    fun getFavoriteFilms() = filmDao.getFavoriteFilms()

    suspend fun addFavoriteFilm(
        filmEntity: FilmEntity
    ) = filmDao.addFavoriteFilm(filmEntity)


    suspend fun removeFromFavorite(
        filmEntity: FilmEntity
    ) = filmDao.removeFromFavorite(filmEntity)

}