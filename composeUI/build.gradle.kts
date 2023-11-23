plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id("com.android.library")
}

kotlin {
    androidTarget()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.compiler.auto)
                implementation(compose.foundation)
                implementation(compose.ui)
                implementation(compose.preview)
                implementation(compose.material)
                implementation(compose.material3)
                implementation(compose.materialIconsExtended)
                implementation(compose.animation)
                implementation(compose.animationGraphics)

                implementation(libs.kamel.image)

                implementation(libs.koin.core)

                implementation(libs.aakira.napier)

                implementation(libs.essenty.lifecycle)

                implementation(project(":shared"))
                implementation(project(":mvi"))
                implementation(project(":composeCore"))
            }
        }
        val androidMain by getting
    }
}

android {
    namespace = "ru.tsutsurin.composeui"
    compileSdk = 34
    defaultConfig {
        minSdk = 23
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
