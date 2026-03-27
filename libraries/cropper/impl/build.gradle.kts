plugins {
    alias(libs.plugins.projectkafka.multiplatform)
    alias(libs.plugins.projectkafka.compose)
    alias(libs.plugins.android.multiplatform)
    alias(libs.plugins.metro)
}

kotlin {
    jvm()
    android {
        namespace = "io.github.shadowrz.projectkafka.libraries.cropper.impl"
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.krop.core)
            implementation(libs.okio)
            implementation(projects.libraries.core)
            implementation(projects.libraries.cropper.api)
            implementation(projects.libraries.fileutils)
            implementation(projects.libraries.mediapickers.api)
            implementation(projects.libraries.di)
        }

        androidMain.dependencies {
            implementation(libs.androidx.core.ktx)
        }
    }
}
