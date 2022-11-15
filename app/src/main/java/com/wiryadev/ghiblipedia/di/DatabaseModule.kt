package com.wiryadev.ghiblipedia.di

import androidx.room.Room
import com.wiryadev.ghiblipedia.data.local.GhibliDb
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            GhibliDb::class.java,
            "ghibli-db"
        ).build()
    }
    single {
        get<GhibliDb>().filmDao()
    }
}