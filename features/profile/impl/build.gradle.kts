plugins {
    id("io.github.shadowrz.projectkafka.compose-module")
    id("io.github.shadowrz.projectkafka.metro-module")
    id("io.github.shadowrz.projectkafka.multiplatform-module")
    id("io.github.shadowrz.projectkafka.library")
}

kotlin {
    android {
        namespace = "io.github.shadowrz.projectkafka.features.profile.impl"
    }

    sourceSets {
        commonMain.dependencies {
            api(projects.features.profile.api)
            implementation(libs.decompose.compose)
            implementation(projects.libraries.core)
            implementation(projects.libraries.data.api)
            implementation(projects.libraries.di)
            implementation(projects.libraries.icons)
        }

        androidMain.dependencies {
            implementation(projects.assets)
            implementation(projects.libraries.components)
            implementation(projects.libraries.strings)
        }
    }
}
