package com.geektech.less4youtubeparcer1kt

import android.app.Application
import com.geektech.less4youtubeparcer1kt.di.koinModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(koinModules)
        }
    }
}