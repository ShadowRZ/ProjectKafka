import io.github.shadowrz.projectkafka.gradle.plugins.extensions.allFeaturesApi

plugins {
    id("io.github.shadowrz.projectkafka.compose-module")
    id("io.github.shadowrz.projectkafka.metro-module")
    id("io.github.shadowrz.projectkafka.multiplatform-module")
    id("io.github.shadowrz.projectkafka.library")
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    android {
        namespace = "io.github.shadowrz.projectkafka.navigation"
    }

    sourceSets {
        commonMain.dependencies {
            // Add All features API
            allFeaturesApi(project)
            implementation(libs.circuit.sharedelements)
            implementation(libs.decompose)
            implementation(libs.decompose.compose.experimental)
            implementation(libs.essenty.coroutines)
            implementation(projects.libraries.architecture)
            implementation(projects.libraries.components)
            implementation(projects.libraries.core)
            implementation(projects.libraries.data.api)
            implementation(projects.libraries.di)
            implementation(projects.libraries.preferences.api)
        }

        remove(commonTest.get())
    }
}
