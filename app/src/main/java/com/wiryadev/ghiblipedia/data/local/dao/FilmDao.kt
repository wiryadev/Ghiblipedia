package com.wiryadev.ghiblipedia.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.wiryadev.ghiblipedia.data.local.entity.FilmEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FilmDao {

    @Query("SELECT * FROM films")
    fun getFavoriteFilms(): Flow<List<FilmEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavoriteFilm(filmEntity: FilmEntity)

    @Delete
    suspend fun removeFromFavorite(filmEntity: FilmEntity)

}