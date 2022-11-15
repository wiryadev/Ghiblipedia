package com.wiryadev.ghiblipedia.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.wiryadev.ghiblipedia.data.local.dao.FilmDao
import com.wiryadev.ghiblipedia.data.local.entity.FilmEntity

@Database(
    entities = [FilmEntity::class],
    version = 1,
)
abstract class GhibliDb : RoomDatabase() {
    abstract fun filmDao(): FilmDao
}