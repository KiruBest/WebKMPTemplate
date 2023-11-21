package com.tsutsurin.androidapp

import android.app.Application
import com.tsutsurin.androidapp.di.androidModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import ru.tsutsurin.webkmp.di.sharedModule

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            androidLogger()
            modules(sharedModule() + androidModule)
        }
    }
}