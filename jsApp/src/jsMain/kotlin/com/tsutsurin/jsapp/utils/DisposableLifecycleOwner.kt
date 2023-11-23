package com.tsutsurin.jsapp.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import com.arkivanov.essenty.lifecycle.Lifecycle
import com.arkivanov.essenty.lifecycle.LifecycleOwner
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.lifecycle.destroy
import com.arkivanov.essenty.lifecycle.resume

@Composable
fun DisposableLifecycleOwner(): LifecycleOwner {
    val lifecycleRegistry = LifecycleRegistry()
    val lifecycleOwner = remember {
        object : LifecycleOwner {
            override val lifecycle: Lifecycle = lifecycleRegistry
        }
    }

    DisposableEffect(lifecycleRegistry) {
        lifecycleRegistry.resume()
        onDispose(lifecycleRegistry::destroy)
    }

    return lifecycleOwner
}
