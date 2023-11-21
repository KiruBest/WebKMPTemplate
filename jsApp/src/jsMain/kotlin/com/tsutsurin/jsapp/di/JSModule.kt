package com.tsutsurin.jsapp.di

import org.koin.core.context.startKoin
import org.koin.dsl.module
import ru.tsutsurin.webkmp.di.sharedModule

val jsModule = module {

}

fun initKoin() {
    startKoin {
        modules(sharedModule() + jsModule)
    }
}