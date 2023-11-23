package com.tsutsurin.composecore.lifecycle

import com.arkivanov.essenty.lifecycle.Lifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import ru.tsutsurin.webkmp.util.platformDispatchers
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.resume

suspend fun Lifecycle.repeatOnLifecycle(
    context: CoroutineContext = platformDispatchers.main,
    state: Lifecycle.State,
    block: suspend CoroutineScope.() -> Unit
) {
    require(state !== Lifecycle.State.INITIALIZED) {
        "repeatOnLifecycle cannot start work with the INITIALIZED lifecycle state."
    }

    if (this.state === Lifecycle.State.DESTROYED) {
        return
    }

    // This scope is required to preserve context before we move to Dispatchers.Main
    coroutineScope {
        withContext(context) {
            // Check the current state of the lifecycle as the previous check is not guaranteed
            // to be done on the main thread.
            if (this@repeatOnLifecycle.state === Lifecycle.State.DESTROYED) return@withContext

            var listener: EssentyLifecycleListener? = null

            try {
                // Suspend the coroutine until the lifecycle is destroyed or
                // the coroutine is cancelled
                suspendCancellableCoroutine { continuation ->
                    when (state) {
                        Lifecycle.State.INITIALIZED,
                        Lifecycle.State.DESTROYED -> {
                            continuation.resume(Unit)
                        }

                        Lifecycle.State.CREATED -> {
                            listener = CreateDestroyListener(
                                coroutineScope = this@coroutineScope,
                                block = block
                            )
                        }

                        Lifecycle.State.STARTED -> {
                            listener = StartStopListener(
                                coroutineScope = this@coroutineScope,
                                block = block
                            )
                        }

                        Lifecycle.State.RESUMED -> {
                            listener = ResumePauseListener(
                                coroutineScope = this@coroutineScope,
                                block = block
                            )
                        }
                    }
                    listener?.let { subscribe(it) }
                }
            } finally {
                listener?.let {
                    it.cancelListener()
                    unsubscribe(it)
                }
            }
        }
    }
}
