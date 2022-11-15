package com.wiryadev.ghiblipedia.data

import com.wiryadev.ghiblipedia.data.local.LocalDataSource
import com.wiryadev.ghiblipedia.data.local.entity.FilmEntity
import com.wiryadev.ghiblipedia.data.local.entity.asEntity
import com.wiryadev.ghiblipedia.data.local.entity.asExternalModel
import com.wiryadev.ghiblipedia.data.remote.RemoteDataSource
import com.wiryadev.ghiblipedia.data.remote.model.FilmDto
import com.wiryadev.ghiblipedia.data.remote.model.asExternalModel
import com.wiryadev.ghiblipedia.model.Film
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class GhibliRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
) : GhibliRepository {

    override fun getFilms(): Flow<List<Film>> = flow {
        emit(remoteDataSource.getFilms().map(FilmDto::asExternalModel))
    }

    override fun getFilmDetail(filmId: String): Flow<Film> = flow {
        emit(remoteDataSource.getFilmDetail(filmId).asExternalModel())
    }

    override fun getFavoriteFilms(): Flow<List<Film>> = localDataSource.getFavoriteFilms()
        .map { it.map(FilmEntity::asExternalModel) }

    override fun checkFavorite(filmId: String): Flow<Int> = localDataSource.checkFavorite(filmId)

    override suspend fun addFavoriteFilm(
        film: Film
    ) = localDataSource.addFavoriteFilm(film.asEntity())

    override suspend fun removeFromFavorite(
       film: Film
    ) = localDataSource.removeFromFavorite(film.asEntity())


}