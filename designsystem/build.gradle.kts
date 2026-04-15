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
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.circuit.sharedelements)
            implementation(libs.coil.compose)
            implementation(libs.compose.material3)
            implementation(libs.compose.material3.adaptive)
            implementation(libs.decompose)
            implementation(libs.decompose.compose.experimental)
            implementation(projects.libraries.strings)
        }

        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
        }
    }
}
