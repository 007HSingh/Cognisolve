plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("org.jetbrains.compose")
    id("org.jetbrains.kotlin.plugin.compose")
}

kotlin {
    js(IR) {
        browser()
    }

    sourceSets {
        val jsMain by getting {
            dependencies {
                implementation(compose.html.core)
                implementation(compose.runtime)

                // Ktor client
                implementation("io.ktor:ktor-client-core:3.1.1")
                implementation("io.ktor:ktor-client-js:3.1.1")
                implementation("io.ktor:ktor-client-content-negotiation:3.1.1")
                implementation("io.ktor:ktor-serialization-kotlinx-json:3.1.1")

                // Kotlinx
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.0")
                api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.1")
            }
        }
    }
}

