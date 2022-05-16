package com.ekotyoo.racana

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber.*
import timber.log.Timber.Forest.plant


@HiltAndroidApp
class RacanaApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            plant(DebugTree())
        }
    }
}