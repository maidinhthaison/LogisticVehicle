package com.linkon.main

import androidx.multidex.MultiDexApplication
import com.linkon.BuildConfig
import com.linkon.config.AppConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MainApp : MultiDexApplication() {
    companion object {
        lateinit var instance: MainApp
            private set
    }

    override fun onCreate() {
        super.onCreate()
        AppConfig.setBackendEnvironment(BuildConfig.FLAVOR)
        instance = this
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(ReleaseTree())
        }
    }

}