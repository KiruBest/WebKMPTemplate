package com.tsutsurin.mvi.base

sealed interface MVI {
    interface Intent : MVI
    interface SideEffect : MVI
    interface Action : MVI
    interface Message : MVI

    interface State : MVI {
        val contentState: ContentState
        val content: Content
    }
}