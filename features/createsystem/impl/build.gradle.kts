plugins {
    id("io.github.shadowrz.projectkafka.feature")
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    android {
        namespace = "io.github.shadowrz.projectkafka.features.createsystem.impl"
    }

    sourceSets {
        commonMain.dependencies {
            api(projects.features.createsystem.api)
            implementation(libs.decompose.compose.experimental)
            implementation(libs.krop.core)
            implementation(projects.libraries.components)
            implementation(projects.libraries.core)
            implementation(projects.libraries.data.api)
            implementation(projects.libraries.icons)
            implementation(projects.libraries.preferences.api)
            implementation(projects.libraries.profile.api)
            implementation(projects.libraries.profile.components)
            implementation(projects.libraries.strings)
        }
    }
}
