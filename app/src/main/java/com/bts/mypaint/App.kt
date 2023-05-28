package com.bts.mypaint

import android.app.Application
import androidcommon.extension.plantLogger
import com.bts.mypaint.data.Prefs
import com.bts.mypaint.di.*
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {
    private val prefs by inject<Prefs>()
    companion object {
        lateinit var instance: App
    }
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(appModule, viewModelModule, storageModule, netModule, repositoryModule)
            androidLogger()
        }
        instance = this
        plantLogger()
    }
}