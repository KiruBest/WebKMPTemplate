package com.tsutsurin.mvi.base

import com.arkivanov.mvikotlin.core.store.Bootstrapper
import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import org.koin.core.component.KoinComponent

abstract class BaseViewModel<Intent : MVI.Intent,
        Action : MVI.Action,
        State : MVI.State,
        Message : MVI.Message,
        SideEffect : MVI.SideEffect>(private val storeFactory: StoreFactory) : KoinComponent {

    protected open val name: String? = this::class.simpleName
    protected open val autoInit: Boolean = true
    protected abstract val initialState: State

    private val store: Store<Intent, State, SideEffect> = createStore()

    val stateFlow = store.stateFlow
    val sideEffect = store.labels

    private fun createStore() = storeFactory.create(
        name = name,
        autoInit = true,
        initialState = initialState,
        bootstrapper = bootstrapper(),
        executorFactory = executorFactory(),
        reducer = reducer(),
    )

    protected open fun bootstrapper(): Bootstrapper<Action>? = null

    protected abstract fun executorFactory():
                () -> Executor<Intent, Action, State, Message, SideEffect>

    protected open fun reducer(): Reducer<State, Message> = Reducer { this }

    fun init() {
        if (!autoInit) store.init()
    }

    fun sendIntent(intent: Intent) {
        store.accept(intent)
    }

    open fun onCleared() {
        store.dispose()
    }
}
