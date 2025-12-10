plugins {
    id("io.github.shadowrz.projectkafka.feature")
}

kotlin {
    android {
        namespace = "io.github.shadowrz.projectkafka.features.profile.impl"

        androidResources {
            enable = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            api(projects.features.profile.api)
            implementation(libs.decompose.compose)
            implementation(libs.hanekokoro.framework.runtime)
            implementation(projects.libraries.core)
            implementation(projects.libraries.data.api)
            implementation(projects.libraries.di)
            implementation(projects.libraries.icons)
        }

        androidMain.dependencies {
            implementation(libs.coil.compose)
            implementation(projects.assets)
            implementation(projects.libraries.components)
            implementation(projects.libraries.strings)
        }
    }
}
