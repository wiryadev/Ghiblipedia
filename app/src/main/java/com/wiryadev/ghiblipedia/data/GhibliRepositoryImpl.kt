package com.wiryadev.ghiblipedia.data

import com.wiryadev.ghiblipedia.data.remote.RemoteDataSource
import com.wiryadev.ghiblipedia.model.Film
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GhibliRepositoryImpl(private val remoteDataSource: RemoteDataSource) : GhibliRepository {

    override suspend fun getFilms(): Result<List<Film>> {
        return withContext(Dispatchers.IO) {
            try {
                val films = remoteDataSource.getFilms()
                Result.Success(films)
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
    }

    override suspend fun getFilmDetail(filmId: String): Result<Film> {
        return withContext(Dispatchers.IO) {
            try {
                val film = remoteDataSource.getFilmDetail(filmId)
                Result.Success(film)
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
    }
}