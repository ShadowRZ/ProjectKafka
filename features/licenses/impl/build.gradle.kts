plugins {
    id("io.github.shadowrz.projectkafka.feature")
    id("io.github.shadowrz.projectkafka.kotest")
}

kotlin {
    jvm()
    android {
        namespace = "io.github.shadowrz.projectkafka.features.licenses.impl"
    }

    sourceSets {
        commonMain.dependencies {
            api(projects.features.licenses.api)
            implementation(libs.aboutlibraries.core)
            implementation(projects.libraries.architecture)
            implementation(projects.libraries.icons)
            implementation(projects.libraries.strings)
        }

        androidMain.dependencies {
            implementation(libs.aboutlibraries.compose.material3)
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
    }
}
