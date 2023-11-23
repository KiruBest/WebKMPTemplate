package com.tsutsurin.composeui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arkivanov.essenty.lifecycle.LifecycleOwner
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.tsutsurin.composecore.collectAsStateWithLifecycle
import com.tsutsurin.composecore.collectWithLifecycle
import com.tsutsurin.composecore.lifecycle.EmptyLifecycleOwner
import com.tsutsurin.composecore.lifecycle.LocalLifecycleOwner
import com.tsutsurin.composeui.di.DI
import com.tsutsurin.composeui.theme.WebKMPTheme
import com.tsutsurin.mvi.main.MainSideEffect
import ru.tsutsurin.webkmp.Greeting

@Composable
fun App(
    modifier: Modifier = Modifier,
    darkTheme: Boolean = isSystemInDarkTheme(),
    lifecycleOwner: LifecycleOwner = EmptyLifecycleOwner(),
    onThemeApplied: @Composable (darkTheme: Boolean) -> Unit = {}
) {
    val currentDarkTheme = remember { mutableStateOf(darkTheme) }
    CompositionLocalProvider(LocalLifecycleOwner provides lifecycleOwner) {
        WebKMPTheme(darkTheme = currentDarkTheme.value) {
            onThemeApplied(currentDarkTheme.value)
            // A surface container using the 'background' color from the theme
            Surface(
                modifier = Modifier.then(modifier).fillMaxSize(),
                color = colorScheme.background
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    //test
                    val mainViewModel = remember { DI.mainViewModel }
                    LocalLifecycleOwner.current.lifecycle.doOnDestroy {
                        mainViewModel.onCleared()
                    }
                    val text = remember { mutableStateOf(Greeting().greeting()) }
                    val state = mainViewModel.stateFlow.collectAsStateWithLifecycle()
                    mainViewModel.sideEffect.collectWithLifecycle {
                        when(it) {
                            is MainSideEffect.Show -> text.value = it.text
                        }
                    }
                    //test

                    Greeting(text.value)
                    Spacer(modifier = Modifier.fillMaxWidth().height(8.dp))
                    Button(onClick = {
                        currentDarkTheme.value = currentDarkTheme.value.not()
                    }) {
                        val themeTitle = if (currentDarkTheme.value) {
                            "Dark"
                        } else {
                            "Light"
                        }
                        Text(text = "Current Theme: $themeTitle")
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WebKMPTheme {
        Greeting("Android")
    }
}
