plugins {
    id("io.github.shadowrz.projectkafka.compose-module")
    id("io.github.shadowrz.projectkafka.metro-module")
    id("io.github.shadowrz.projectkafka.multiplatform-module")
    id("io.github.shadowrz.projectkafka.library")
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    android {
        namespace = "io.github.shadowrz.projectkafka.features.createsystem.impl"

        androidResources {
            enable = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            api(projects.features.createsystem.api)
            implementation(libs.decompose)
            implementation(libs.decompose.compose.experimental)
            implementation(libs.hanekokoro.framework.runtime)
            implementation(projects.libraries.core)
            implementation(projects.libraries.data.api)
            implementation(projects.libraries.icons)
            implementation(projects.libraries.preferences.api)
        }

        androidMain.dependencies {
            implementation(libs.krop.core)
            implementation(projects.libraries.components)
            implementation(projects.libraries.data.api)
            implementation(projects.libraries.icons)
            implementation(projects.libraries.profile.api)
            implementation(projects.libraries.profile.components)
            implementation(projects.libraries.strings)
        }
    }
}
