plugins {
    id("io.github.shadowrz.projectkafka.compose-module")
    id("io.github.shadowrz.projectkafka.metro-module")
    id("io.github.shadowrz.projectkafka.multiplatform-module")
    id("io.github.shadowrz.projectkafka.library")
}

kotlin {
    android {
        namespace = "io.github.shadowrz.projectkafka.features.editmember.impl"

        androidResources {
            enable = true
        }

        withHostTest {
            isReturnDefaultValues = true
            isIncludeAndroidResources = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            api(projects.features.editmember.api)
            implementation(libs.decompose)
            implementation(libs.decompose.jetpack)
            implementation(projects.libraries.data.api)
            implementation(projects.libraries.di)
            implementation(projects.libraries.icons)
        }

        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
            implementation(libs.krop.core)
            implementation(projects.libraries.components)
            implementation(projects.libraries.profile.api)
            implementation(projects.libraries.profile.components)
            implementation(projects.libraries.profile.test)
            implementation(projects.libraries.strings)
        }

        val androidHostTest by getting

        androidHostTest.dependencies {
            implementation(libs.junit)
            implementation(libs.kotest.assertions)
            implementation(libs.kotlin.test)
            implementation(libs.kotlinx.coroutines.test)
            implementation(libs.molecule.runtime)
            implementation(libs.turbine)
            implementation(libs.uri)
            implementation(projects.libraries.data.test)
            implementation(projects.libraries.mediapickers.test)
            implementation(projects.libraries.profile.test)
            implementation(projects.tests.utils)
        }
    }
}
