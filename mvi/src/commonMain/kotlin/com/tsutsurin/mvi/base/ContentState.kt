package com.tsutsurin.mvi.base

sealed interface ContentState {
    class Loading(
//        val text: StringDesc? = SharedRes.strings.base_process_loading.desc(),
//        val onBackPressed: (() -> Unit)? = null,
//        val textSize: Int = 14,
    ) : ContentState

    data object Content : ContentState
    class Result(
//        val model: ResultModel
    ) : ContentState
}