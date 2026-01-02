plugins {
    id("io.github.shadowrz.projectkafka.feature")
    id("io.github.shadowrz.projectkafka.kotest")
}

kotlin {
    jvm()
    android {
        namespace = "io.github.shadowrz.projectkafka.features.editmember.impl"

        androidResources {
            enable = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            api(projects.features.editmember.api)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.kotlinx.datetime)
            implementation(libs.krop.core)
            implementation(projects.libraries.architecture)
            implementation(projects.libraries.components)
            implementation(projects.libraries.core)
            implementation(projects.libraries.data.api)
            implementation(projects.libraries.di)
            implementation(projects.libraries.icons)
            implementation(projects.libraries.profile.api)
            implementation(projects.libraries.profile.components)
            implementation(projects.libraries.profile.test)
            implementation(projects.libraries.strings)
        }

        commonTest.dependencies {
            implementation(libs.kotest.assertions)
            implementation(libs.kotlinx.coroutines.test)
            implementation(libs.molecule.runtime)
            implementation(libs.turbine)
            implementation(libs.uri)
            implementation(projects.libraries.data.test)
            implementation(projects.libraries.mediapickers.test)
            implementation(projects.libraries.profile.test)
            implementation(projects.tests.utils)
        }

        jvmTest.dependencies {
            implementation(libs.kotest.runner.junit5)
        }
    }
}
