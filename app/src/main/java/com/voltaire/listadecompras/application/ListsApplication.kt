package com.voltaire.listadecompras.application

import android.app.Application
import com.voltaire.listadecompras.di.modules.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ListsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ListsApplication)
            modules(appModules)
        }
    }
}