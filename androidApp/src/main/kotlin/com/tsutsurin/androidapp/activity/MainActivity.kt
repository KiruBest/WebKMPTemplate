package com.tsutsurin.androidapp.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.arkivanov.essenty.lifecycle.Lifecycle
import com.arkivanov.essenty.lifecycle.LifecycleOwner
import com.arkivanov.essenty.lifecycle.essentyLifecycle
import com.tsutsurin.composeui.App

class MainActivity : ComponentActivity() {
    private var essentyLifecycleOwner: LifecycleOwner? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        essentyLifecycleOwner = object : LifecycleOwner {
            override val lifecycle: Lifecycle = essentyLifecycle()
        }

        setContent {
            App(
                modifier = Modifier.systemBarsPadding(),
                lifecycleOwner = essentyLifecycleOwner!!,
                onThemeApplied = {
                    WindowInsets(darkTheme = it)
                }
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        essentyLifecycleOwner = null
    }

    @Composable
    private fun WindowInsets(darkTheme: Boolean) {
        val view = LocalView.current
        if (!view.isInEditMode) {
            val systemBarColor = colorScheme.primary.toArgb()
            SideEffect {
                window?.let {
                    WindowCompat.setDecorFitsSystemWindows(window, false)
                    window.statusBarColor = systemBarColor
                    window.navigationBarColor = systemBarColor
                    WindowCompat.getInsetsController(window, view)
                        .isAppearanceLightStatusBars = darkTheme
                }
            }
        }
    }
}
