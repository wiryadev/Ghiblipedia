package com.wiryadev.ghiblipedia.di

import com.wiryadev.ghiblipedia.data.FakeDao
import com.wiryadev.ghiblipedia.data.local.dao.FilmDao
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val databaseTestModule = module {
    singleOf(::FakeDao) bind FilmDao::class
}