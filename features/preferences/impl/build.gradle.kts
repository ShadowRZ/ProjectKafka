plugins {
    id("io.github.shadowrz.projectkafka.compose-module")
    id("io.github.shadowrz.projectkafka.metro-module")
    id("io.github.shadowrz.projectkafka.multiplatform-module")
    id("io.github.shadowrz.projectkafka.library")
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
            implementation(libs.decompose)
            implementation(libs.decompose.compose.experimental)
            implementation(libs.hanekokoro.framework.runtime)
            implementation(projects.libraries.components)
            implementation(projects.libraries.preferences.api)
        }

        androidMain.dependencies {
            implementation(projects.libraries.icons)
            implementation(projects.libraries.strings)
        }
    }
}
