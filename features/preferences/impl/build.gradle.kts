plugins {
    id("io.github.shadowrz.projectkafka.feature")
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    android {
        namespace = "io.github.shadowrz.projectkafka.features.preferences.impl"

        androidResources {
            enable = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            api(projects.features.preferences.api)
            implementation(libs.circuit.sharedelements)
            implementation(libs.decompose.compose.experimental)
            implementation(projects.libraries.components)
            implementation(projects.libraries.preferences.api)
        }

        androidMain.dependencies {
            implementation(projects.libraries.icons)
            implementation(projects.libraries.strings)
        }
    }
}
