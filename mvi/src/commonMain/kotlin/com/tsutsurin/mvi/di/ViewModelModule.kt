package com.tsutsurin.mvi.di

import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.tsutsurin.mvi.main.MainViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

val viewModelModule = module {
    factoryOf(::DefaultStoreFactory).bind(StoreFactory::class)
    factoryOf(::MainViewModel)
}