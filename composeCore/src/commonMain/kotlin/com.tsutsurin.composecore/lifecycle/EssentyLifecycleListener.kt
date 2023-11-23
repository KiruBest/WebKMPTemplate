package com.tsutsurin.composecore.lifecycle

import com.arkivanov.essenty.lifecycle.Lifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

abstract class EssentyLifecycleListener: Lifecycle.Callbacks  {
    protected abstract val coroutineScope: CoroutineScope
    protected abstract val block: suspend CoroutineScope.() -> Unit

    private val mutex = Mutex()
    private var launchedJob: Job? = null

    protected fun launchListener() {
        launchedJob = coroutineScope.launch {
            // Mutex makes invocations run serially,
            // coroutineScope ensures all child coroutines finish
            mutex.withLock {
                coroutineScope {
                    block()
                }
            }
        }
    }

    protected fun stopListener() {
        cancelListener()
        launchedJob = null
    }

    fun cancelListener() {
        launchedJob?.cancel()
    }
}

class CreateDestroyListener(
    override val coroutineScope: CoroutineScope,
    override val block: suspend CoroutineScope.() -> Unit
) : EssentyLifecycleListener() {
    override fun onCreate() {
        launchListener()
        super.onCreate()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopListener()
    }
}

class StartStopListener(
    override val coroutineScope: CoroutineScope,
    override val block: suspend CoroutineScope.() -> Unit
) : EssentyLifecycleListener() {
    override fun onStart() {
        launchListener()
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
        stopListener()
    }
}

class ResumePauseListener(
    override val coroutineScope: CoroutineScope,
    override val block: suspend CoroutineScope.() -> Unit
) : EssentyLifecycleListener() {
    override fun onResume() {
        launchListener()
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        stopListener()
    }
}
