plugins {
    alias(libs.plugins.projectkafka.compose)
    alias(libs.plugins.projectkafka.multiplatform)
    alias(libs.plugins.android.multiplatform)
}

kotlin {
    jvm()
    android {
        namespace = "io.github.shadowrz.projectkafka.designsystem"

        androidResources {
            enable = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            api(libs.compose.material3)
            api(libs.decompose)
            api(libs.decompose.compose.experimental)
            implementation(libs.circuit.sharedelements)
            implementation(libs.coil.compose)
            implementation(libs.compose.material3.adaptive)
            implementation(projects.assets)
            implementation(projects.libraries.strings)
        }

        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
        }

        remove(commonTest.get())
    }
}
