plugins {
    id("io.github.shadowrz.projectkafka.compose-module")
    id("io.github.shadowrz.projectkafka.multiplatform-module")
    id("io.github.shadowrz.projectkafka.library")
}

kotlin {
    android {
        namespace = "io.github.shadowrz.projectkafka.libraries.components"
    }

    sourceSets {
        commonMain.dependencies {
            api(projects.libraries.core)
            implementation(libs.androidx.material3.adaptive.multiplatform)
            implementation(libs.circuit.sharedelements)
            implementation(libs.decompose.compose.experimental)
            implementation(libs.essenty.backhandler)
            implementation(libs.kotlinx.datetime)
            // Coil
            implementation(libs.coil.compose)
            // Icons
            implementation(projects.libraries.icons)
        }

        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
            implementation(libs.coil.gif)
            implementation(projects.assets)
            implementation(projects.libraries.strings)
        }

        remove(commonTest.get())
    }
}
