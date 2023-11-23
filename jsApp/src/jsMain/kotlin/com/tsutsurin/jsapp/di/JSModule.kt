package com.tsutsurin.jsapp.di

import com.tsutsurin.mvi.di.viewModelModule
import com.tsutsurin.mvi.main.MainViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import org.koin.dsl.module
import ru.tsutsurin.webkmp.di.sharedModule

val jsModule = module {

}

fun initKoin() {
    startKoin {
        modules(sharedModule() + viewModelModule + jsModule)
    }
}

object DI: KoinComponent {
    val mainViewModel: MainViewModel by inject()
}
