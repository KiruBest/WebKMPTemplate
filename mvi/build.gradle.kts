plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

kotlin {
    androidTarget()
    js(IR) {
        browser()
        nodejs()
    }

    sourceSets {
        all {
            languageSettings.optIn("com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi")
            languageSettings.optIn("kotlinx.coroutines.ExperimentalCoroutinesApi")
        }
        /* Main source sets */
        val commonMain by getting {
            dependencies {
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.koin.core)
                implementation(libs.mvikotlin.core)
                implementation(libs.mvikotlin.main)
                implementation(libs.mvikotlin.extensions.coroutines)

                implementation(libs.aakira.napier)

                implementation(project(":shared"))
            }
        }
        val androidMain by getting
        val jsMain by getting
    }
}

android {
    namespace = "ru.tsutsurin.mvi"
    compileSdk = 34
    defaultConfig {
        minSdk = 23
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
