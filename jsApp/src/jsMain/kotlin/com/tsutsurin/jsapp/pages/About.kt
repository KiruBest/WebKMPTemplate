package com.tsutsurin.jsapp.pages

import androidx.compose.runtime.Composable
import com.tsutsurin.jsapp.components.layouts.PageContentStyle
import com.tsutsurin.jsapp.components.layouts.PageLayout
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.components.style.toModifier
import com.varabyte.kobweb.silk.components.text.SpanText

@Page
@Composable
fun AboutPage() {
    PageLayout("About") {
        SpanText(
            text = "HelloWorld!",
            modifier = PageContentStyle.toModifier()
        )
    }
}