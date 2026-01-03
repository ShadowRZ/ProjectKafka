plugins {
    id("io.github.shadowrz.projectkafka.feature")
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    android {
        namespace = "io.github.shadowrz.projectkafka.features.home.impl"
    }

    sourceSets {
        commonMain.dependencies {
            api(projects.features.home.api)
            implementation(libs.androidx.material3.adaptive)
            implementation(libs.circuit.sharedelements)
            implementation(libs.composeunstyled.primitives)
            implementation(libs.decompose.compose)
            implementation(libs.decompose.compose.experimental)
            implementation(projects.features.fronterindicator.api)
            implementation(projects.features.profile.api)
            implementation(projects.libraries.components)
            implementation(projects.libraries.data.api)
            implementation(projects.libraries.di)
            implementation(projects.libraries.icons)
            implementation(projects.libraries.preferences.api)
            implementation(projects.libraries.strings)
        }

        androidMain.dependencies {
            implementation(libs.androidx.core.ktx)
            implementation(libs.androidx.paging.compose)
        }
    }
}
