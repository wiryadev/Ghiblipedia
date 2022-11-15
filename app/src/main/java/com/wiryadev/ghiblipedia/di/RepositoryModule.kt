package com.wiryadev.ghiblipedia.di

import com.wiryadev.ghiblipedia.data.GhibliRepository
import com.wiryadev.ghiblipedia.data.GhibliRepositoryImpl
import com.wiryadev.ghiblipedia.data.local.LocalDataSource
import com.wiryadev.ghiblipedia.data.remote.RemoteDataSource
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoryModule = module {
    singleOf(::LocalDataSource)
    singleOf(::RemoteDataSource)
    singleOf(::GhibliRepositoryImpl) bind GhibliRepository::class
}