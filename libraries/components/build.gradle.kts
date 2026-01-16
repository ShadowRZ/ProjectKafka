plugins {
    alias(libs.plugins.projectkafka.compose)
    alias(libs.plugins.projectkafka.multiplatform)
    alias(libs.plugins.android.multiplatform)
}

kotlin {
    jvm()
    android {
        namespace = "io.github.shadowrz.projectkafka.libraries.components"

        androidResources {
            enable = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            api(projects.libraries.core)
            implementation(libs.circuit.sharedelements)
            implementation(libs.compose.material3.adaptive)
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
            implementation(projects.libraries.androidutils)
            implementation(projects.libraries.strings)
        }

        remove(commonTest.get())
    }
}
