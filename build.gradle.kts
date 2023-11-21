subprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven("https://us-central1-maven.pkg.dev/varabyte-repos/public")
    }
}

plugins {
    alias(libs.plugins.org.jetbrains.kotlin.jvm) apply false
    @Suppress("DSL_SCOPE_VIOLATION")
    alias(libs.plugins.androidApplication) apply false
    @Suppress("DSL_SCOPE_VIOLATION")
    alias(libs.plugins.kotlinAndroid) apply false
    @Suppress("DSL_SCOPE_VIOLATION")
    kotlin("multiplatform").version(libs.versions.kotlin).apply(false)
    @Suppress("DSL_SCOPE_VIOLATION")
    kotlin("plugin.serialization").version(libs.versions.kotlin).apply(false)
    @Suppress("DSL_SCOPE_VIOLATION")
    id("org.jetbrains.compose").version(libs.versions.jetbrains.compose).apply(false)
    @Suppress("DSL_SCOPE_VIOLATION")
    id("com.android.library").version(libs.versions.agp).apply(false)
    @Suppress("DSL_SCOPE_VIOLATION")
    id("com.squareup.sqldelight").version(libs.versions.sqldelight).apply(false)
    @Suppress("DSL_SCOPE_VIOLATION")
    id("com.varabyte.kobweb.application").version(libs.versions.kobweb).apply(false)
    @Suppress("DSL_SCOPE_VIOLATION")
    id("com.varabyte.kobweb.library").version(libs.versions.kobweb).apply(false)
    @Suppress("DSL_SCOPE_VIOLATION")
    id("com.varabyte.kobwebx.markdown").version(libs.versions.kobweb).apply(false)
}
