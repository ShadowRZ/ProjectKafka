plugins {
    alias(libs.plugins.projectkafka.multiplatform)
    alias(libs.plugins.projectkafka.compose)
    alias(libs.plugins.android.multiplatform)
    alias(libs.plugins.metro)
}

kotlin {
    jvm()
    androidLibrary {
        namespace = "io.github.shadowrz.projectkafka.libraries.profile.impl"
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.krop.core)
            implementation(libs.okio)
            implementation(projects.libraries.mediapickers.api)
            implementation(projects.libraries.profile.api)
            implementation(projects.libraries.core)
            implementation(projects.libraries.di)
        }

        androidMain.dependencies {
            implementation(libs.androidx.core.ktx)
        }
    }
}
