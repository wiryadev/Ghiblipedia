package com.wiryadev.ghiblipedia.di

import com.wiryadev.ghiblipedia.data.GhibliRepository
import com.wiryadev.ghiblipedia.data.GhibliRepositoryImpl
import com.wiryadev.ghiblipedia.data.remote.RemoteDataSource
import org.koin.dsl.module

val repositoryModule = module {
    single { RemoteDataSource(get()) }
    single<GhibliRepository> {
        GhibliRepositoryImpl(get())
    }
}