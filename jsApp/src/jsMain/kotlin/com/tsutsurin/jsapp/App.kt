package com.tsutsurin.jsapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import com.tsutsurin.composecore.lifecycle.LocalLifecycleOwner
import com.tsutsurin.jsapp.di.initKoin
import com.tsutsurin.jsapp.utils.DisposableLifecycleOwner
import com.varabyte.kobweb.compose.css.ScrollBehavior
import com.varabyte.kobweb.compose.ui.modifiers.minHeight
import com.varabyte.kobweb.compose.ui.modifiers.scrollBehavior
import com.varabyte.kobweb.core.App
import com.varabyte.kobweb.silk.SilkApp
import com.varabyte.kobweb.silk.components.layout.Surface
import com.varabyte.kobweb.silk.components.style.common.SmoothColorStyle
import com.varabyte.kobweb.silk.components.style.toModifier
import com.varabyte.kobweb.silk.init.InitSilk
import com.varabyte.kobweb.silk.init.InitSilkContext
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import kotlinx.browser.localStorage
import org.jetbrains.compose.web.css.vh

private const val COLOR_MODE_KEY = "kobweb:colorMode"

@InitSilk
fun initColorMode(ctx: InitSilkContext) {
    ctx.config.initialColorMode = localStorage.getItem(COLOR_MODE_KEY)?.let {
        ColorMode.valueOf(it)
    } ?: ColorMode.DARK
}

@App
@Composable
fun App(content: @Composable () -> Unit) {
    initKoin()
    val lifecycleOwner = DisposableLifecycleOwner()
    CompositionLocalProvider(LocalLifecycleOwner provides lifecycleOwner) {
        AppUI(content)
    }
}

@Composable
fun AppUI(content: @Composable () -> Unit) {
    SilkApp {
        val colorMode = ColorMode.current
        LaunchedEffect(colorMode) {
            localStorage.setItem(COLOR_MODE_KEY, colorMode.name)
        }

        Surface(
            SmoothColorStyle.toModifier()
                .minHeight(100.vh)
                .scrollBehavior(ScrollBehavior.Smooth)
        ) {
            content()
        }
    }
}