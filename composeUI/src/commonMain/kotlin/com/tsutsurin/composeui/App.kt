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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tsutsurin.composeui.theme.WebKMPTheme
import ru.tsutsurin.webkmp.Greeting

@Composable
fun App(
    modifier: Modifier = Modifier,
    darkTheme: Boolean = isSystemInDarkTheme(),
    onThemeApplied: @Composable (darkTheme: Boolean) -> Unit = {}
) {
    val currentDarkTheme = remember { mutableStateOf(darkTheme) }
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
                Greeting(Greeting().greeting())
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
