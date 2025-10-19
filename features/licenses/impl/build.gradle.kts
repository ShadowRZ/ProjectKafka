plugins {
    id("io.github.shadowrz.projectkafka.compose-module")
    id("io.github.shadowrz.projectkafka.metro-module")
    id("io.github.shadowrz.projectkafka.multiplatform-module")
    id("io.github.shadowrz.projectkafka.library")
}

kotlin {
    android {
        namespace = "io.github.shadowrz.projectkafka.features.licenses.impl"

        withHostTest {
            isReturnDefaultValues = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            api(projects.features.licenses.api)
            implementation(projects.libraries.icons)
        }

        androidMain.dependencies {
            implementation(libs.aboutlibraries.compose.material3)
            implementation(projects.libraries.strings)
        }

        commonTest.dependencies {
            implementation(libs.kotest.assertions)
            implementation(libs.kotlin.test)
            implementation(libs.kotlinx.coroutines.test)
            implementation(libs.molecule.runtime)
            implementation(libs.turbine)
            implementation(projects.tests.utils)
        }
    }
}
