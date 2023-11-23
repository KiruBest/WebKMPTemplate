package ru.tsutsurin.webkmp.util

import kotlinx.coroutines.CoroutineDispatcher

interface PlatformDispatchers {
    val main: CoroutineDispatcher
    val io: CoroutineDispatcher
    val unconfined: CoroutineDispatcher
}

expect val platformDispatchers: PlatformDispatchers