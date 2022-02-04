package com.wiryadev.ghiblipedia.di

import com.squareup.moshi.Moshi
import com.wiryadev.ghiblipedia.BuildConfig
import com.wiryadev.ghiblipedia.data.remote.GhibliService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().setLevel(
                    if (BuildConfig.DEBUG) {
                        HttpLoggingInterceptor.Level.BODY
                    } else {
                        HttpLoggingInterceptor.Level.NONE
                    }
                )
            )
            .connectTimeout(120, TimeUnit.SECONDS)
            .build()
    }
    single {
        Moshi.Builder().build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://ghibliapi.herokuapp.com/")
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .client(get())
            .build()
        retrofit.create(GhibliService::class.java)
    }
}