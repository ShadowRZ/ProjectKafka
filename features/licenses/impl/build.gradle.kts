plugins {
    id("io.github.shadowrz.projectkafka.feature")
    id("io.github.shadowrz.projectkafka.kotest")
}

kotlin {
    jvm()
    android {
        namespace = "io.github.shadowrz.projectkafka.features.licenses.impl"

        withHostTest {
            isReturnDefaultValues = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            api(projects.features.licenses.api)
            implementation(libs.aboutlibraries.core)
            implementation(libs.hanekokoro.framework.runtime)
            implementation(projects.libraries.icons)
        }

        androidMain.dependencies {
            implementation(libs.aboutlibraries.compose.material3)
            implementation(projects.libraries.strings)
        }

        commonTest.dependencies {
            implementation(libs.kotlinx.coroutines.test)
            implementation(libs.molecule.runtime)
            implementation(libs.turbine)
            implementation(projects.tests.utils)
        }

        jvmTest.dependencies {
            implementation(libs.kotest.runner.junit5)
        }

        val androidHostTest by getting

        androidHostTest.dependencies {
            implementation(libs.kotest.runner.junit5)
        }
    }
}
