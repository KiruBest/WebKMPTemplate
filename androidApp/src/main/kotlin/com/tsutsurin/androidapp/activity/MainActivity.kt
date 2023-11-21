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
import com.tsutsurin.composeui.App

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App(
                modifier = Modifier.systemBarsPadding(),
                onThemeApplied = {
                    WindowInsets(darkTheme = it)
                }
            )
        }
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
