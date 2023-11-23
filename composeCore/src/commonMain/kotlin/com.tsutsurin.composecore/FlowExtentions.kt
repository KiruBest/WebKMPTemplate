package com.tsutsurin.composecore

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import com.arkivanov.essenty.lifecycle.Lifecycle
import com.tsutsurin.composecore.lifecycle.LocalLifecycleOwner
import com.tsutsurin.composecore.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import ru.tsutsurin.webkmp.util.platformDispatchers
import kotlin.coroutines.CoroutineContext

@Composable
fun <T : Any?> StateFlow<T>.collectAsStateWithLifecycle(
    context: CoroutineContext = platformDispatchers.main,
    lifecycleState: Lifecycle.State = Lifecycle.State.STARTED
): State<T> {
    val lifecycleOwner = LocalLifecycleOwner.current

    val stateFlowLifecycleAware = remember(context, this, lifecycleOwner) {
        flowWithLifecycle(
            context = context,
            lifecycle = lifecycleOwner.lifecycle,
            minActiveState = lifecycleState
        )
    }

    // Need to access the initial value to convert to State - collectAsState() suppresses this lint warning too
    val initialValue = value
    return stateFlowLifecycleAware.collectAsState(initial = initialValue, context = context)
}

@Composable
fun <T : Any?> Flow<T>.collectWithLifecycle(
    lifecycleState: Lifecycle.State = Lifecycle.State.STARTED,
    sideEffect: (suspend (sideEffect: T) -> Unit)
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(this, lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(state = lifecycleState) {
            collect { sideEffect(it) }
        }
    }
}

@Composable
fun <T : Any?> Flow<T>.collectWithLifecycle(
    context: CoroutineContext,
    lifecycleState: Lifecycle.State = Lifecycle.State.STARTED,
    sideEffect: (suspend (sideEffect: T) -> Unit)
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableCoroutineScope(context) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(context = context, state = lifecycleState) {
            collect { sideEffect(it) }
        }
    }
}

fun <T> Flow<T>.flowWithLifecycle(
    context: CoroutineContext = platformDispatchers.main,
    lifecycle: Lifecycle,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED
): Flow<T> = callbackFlow {
    lifecycle.repeatOnLifecycle(context = context, state = minActiveState) {
        this@flowWithLifecycle.collect {
            send(it)
        }
    }
    close()
}

@Composable
fun DisposableCoroutineScope(context: CoroutineContext, block: suspend CoroutineScope.() -> Unit) {
    DisposableEffect(context) {
        val scope = CoroutineScope(context)
        scope.launch(block = block)
        onDispose(scope::cancel)
    }
}
