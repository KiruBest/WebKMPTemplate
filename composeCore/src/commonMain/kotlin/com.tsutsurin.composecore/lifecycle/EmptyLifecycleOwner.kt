package com.tsutsurin.composecore.lifecycle

import com.arkivanov.essenty.lifecycle.Lifecycle
import com.arkivanov.essenty.lifecycle.LifecycleOwner
import com.arkivanov.essenty.lifecycle.LifecycleRegistry

class EmptyLifecycleOwner : LifecycleOwner {
    override val lifecycle: Lifecycle = LifecycleRegistry()
}