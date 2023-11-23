package com.tsutsurin.composeui.di

import com.tsutsurin.mvi.main.MainViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

val viewModelModule = com.tsutsurin.mvi.di.viewModelModule

object DI: KoinComponent {
    val mainViewModel: MainViewModel by inject()
}