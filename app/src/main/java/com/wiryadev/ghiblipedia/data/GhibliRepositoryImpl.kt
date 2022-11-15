package com.wiryadev.ghiblipedia.data

import com.wiryadev.ghiblipedia.data.local.LocalDataSource
import com.wiryadev.ghiblipedia.data.local.entity.FilmEntity
import com.wiryadev.ghiblipedia.data.remote.RemoteDataSource
import com.wiryadev.ghiblipedia.data.remote.model.FilmDto
import com.wiryadev.ghiblipedia.data.remote.model.asExternalModel
import com.wiryadev.ghiblipedia.model.Film
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

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

    override fun getFavoriteFilms(): Flow<List<FilmEntity>> = localDataSource.getFavoriteFilms()

    override suspend fun addFavoriteFilm(
        filmEntity: FilmEntity
    ) = localDataSource.addFavoriteFilm(filmEntity)

    override suspend fun removeFromFavorite(
        filmEntity: FilmEntity
    ) = localDataSource.removeFromFavorite(filmEntity)


}