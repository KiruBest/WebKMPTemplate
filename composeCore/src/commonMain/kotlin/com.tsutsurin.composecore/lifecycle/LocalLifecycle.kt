package com.tsutsurin.composecore.lifecycle

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import com.arkivanov.essenty.lifecycle.LifecycleOwner

val LocalLifecycleOwner: ProvidableCompositionLocal<LifecycleOwner> = staticCompositionLocalOf {
    EmptyLifecycleOwner()
}
