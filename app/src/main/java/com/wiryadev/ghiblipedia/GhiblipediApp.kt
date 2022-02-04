package com.wiryadev.ghiblipedia

import android.app.Application
import com.wiryadev.ghiblipedia.di.networkModule
import com.wiryadev.ghiblipedia.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class GhiblipediApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(
                level = if (BuildConfig.DEBUG) Level.DEBUG else Level.NONE
            )
            androidContext(this@GhiblipediApp)
            modules(
                listOf(
                    networkModule,
                    repositoryModule,
                )
            )
        }
    }
}