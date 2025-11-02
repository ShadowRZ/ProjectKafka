plugins {
    id("io.github.shadowrz.projectkafka.compose-module")
    id("io.github.shadowrz.projectkafka.metro-module")
    id("io.github.shadowrz.projectkafka.multiplatform-module")
    id("io.github.shadowrz.projectkafka.library")
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
            implementation(libs.decompose)
            implementation(libs.hanekokoro.framework.runtime)
            implementation(libs.okio)
            // Grab system scope graphs
            implementation(projects.appNavigation)
            implementation(projects.libraries.data.api)
            implementation(projects.libraries.di)
            implementation(projects.libraries.zipwriter)
        }
        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
            // Perform VACUUM INTO
            implementation(libs.sqldelight.android)
            implementation(projects.assets)
            implementation(projects.libraries.components)
            implementation(projects.libraries.icons)
            implementation(projects.libraries.strings)
        }

        remove(commonTest.get())
    }
}
