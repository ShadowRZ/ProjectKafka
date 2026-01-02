plugins {
    id("io.github.shadowrz.projectkafka.feature")
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    android {
        namespace = "io.github.shadowrz.projectkafka.features.datamanage.impl"

        androidResources {
            enable = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            api(projects.features.datamanage.api)
            implementation(libs.okio)
            implementation(projects.assets)
            implementation(projects.libraries.components)
            implementation(projects.libraries.data.api)
            implementation(projects.libraries.di)
            implementation(projects.libraries.icons)
            implementation(projects.libraries.strings)
            implementation(projects.libraries.systemgraph)
            implementation(projects.libraries.zipwriter)
        }
        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
            // Perform VACUUM INTO
            implementation(libs.sqldelight.android)
        }

        remove(commonTest.get())
    }
}
