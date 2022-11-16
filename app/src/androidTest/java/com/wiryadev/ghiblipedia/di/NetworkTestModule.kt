package com.wiryadev.ghiblipedia.di

import com.wiryadev.ghiblipedia.data.FakeService
import com.wiryadev.ghiblipedia.data.remote.retrofit.GhibliService
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val networkTestModule = module {
    singleOf(::FakeService) bind GhibliService::class
}