package com.tsutsurin.mvi.main

import com.arkivanov.mvikotlin.core.store.Bootstrapper
import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineExecutorFactory
import com.tsutsurin.mvi.base.BaseViewModel
import kotlinx.coroutines.launch

class MainViewModel(storeFactory: StoreFactory) : BaseViewModel<MainIntent,
        MainAction,
        MainState,
        MainMessage,
        MainSideEffect>(storeFactory) {

    override val initialState: MainState get() = MainState()

    override fun bootstrapper(): Bootstrapper<MainAction> = coroutineBootstrapper {
        launch {
            dispatch(MainAction.Show("Hello"))
        }
    }

    override fun executorFactory():
                () -> Executor<MainIntent, MainAction, MainState, MainMessage, MainSideEffect> =
        coroutineExecutorFactory {
            onAction<MainAction.Show> {
                publish(MainSideEffect.Show(it.text))
            }
            onIntent<MainIntent.Show> {
                publish(MainSideEffect.Show(it.text))
            }
        }

    override fun reducer(): Reducer<MainState, MainMessage> = Reducer { message ->
        when (message) {
            else -> copy()
        }
    }
}
