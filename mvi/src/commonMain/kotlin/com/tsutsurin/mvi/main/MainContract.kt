package com.tsutsurin.mvi.main

import com.tsutsurin.mvi.base.Content
import com.tsutsurin.mvi.base.ContentState
import com.tsutsurin.mvi.base.MVI

data class MainContent(val text: String = "") : Content

sealed interface MainIntent : MVI.Intent {
    data class Show(val text: String): MainIntent
}

sealed interface MainAction : MVI.Action {
    data class Show(val text: String): MainAction
}

sealed interface MainMessage : MVI.Message

data class MainState(
    override val contentState: ContentState = ContentState.Loading(),
    override val content: MainContent = MainContent()
) : MVI.State

sealed interface MainSideEffect : MVI.SideEffect {
    data class Show(val text: String): MainSideEffect
}
