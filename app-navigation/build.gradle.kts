import io.github.shadowrz.projectkafka.gradle.plugins.extensions.allFeaturesApi

plugins {
    id("io.github.shadowrz.projectkafka.feature")
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    jvm()
    android {
        namespace = "io.github.shadowrz.projectkafka.navigation"

        androidResources {
            enable = false
        }
    }

    sourceSets {
        commonMain.dependencies {
            // Add All features API
            allFeaturesApi(project)
            implementation(libs.circuit.sharedelements)
            implementation(libs.decompose.compose.experimental)
            implementation(libs.essenty.coroutines)
            implementation(projects.libraries.architecture)
            implementation(projects.libraries.components)
            implementation(projects.libraries.core)
            implementation(projects.libraries.data.api)
            implementation(projects.libraries.di)
            implementation(projects.libraries.preferences.api)
            implementation(projects.libraries.systemgraph)
        }

        remove(commonTest.get())
    }
}
