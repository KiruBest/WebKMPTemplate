import com.varabyte.kobweb.gradle.application.util.configAsKobwebApplication

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id("com.varabyte.kobweb.application")
    id("com.varabyte.kobwebx.markdown")
}

group = "com.tsutsurin.jsapp"
version = "1.0-SNAPSHOT"

kobweb {
    app {
        index {
            description.set("Powered by Kobweb")
        }
    }
}

kotlin {
    configAsKobwebApplication("jsApp", includeServer = false)

    js(IR) {
        browser()
        nodejs()
    }

    sourceSets {
        val jsMain by getting {
            dependencies {
                implementation(libs.kotlinx.coroutines.core)

                implementation(compose.html.core)
                implementation(compose.html.svg)
                implementation(compose.runtime)

                implementation(libs.kobweb.core)
                implementation(libs.kobweb.silk)

                implementation(libs.koin.core)

                implementation(libs.essenty.lifecycle)

                implementation(project(":shared"))
                implementation(project(":mvi"))
                implementation(project(":composeCore"))
            }
        }
    }
}
