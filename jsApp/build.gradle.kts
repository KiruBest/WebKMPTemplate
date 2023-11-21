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
                implementation(compose.html.core)
                implementation(compose.runtime)

                implementation(libs.kobweb.core)
                implementation(libs.kobweb.silk)

                implementation(project(":shared"))
            }
        }
    }
}
