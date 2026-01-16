plugins {
    alias(libs.plugins.projectkafka.compose)
    alias(libs.plugins.projectkafka.multiplatform)
    alias(libs.plugins.android.multiplatform)
    alias(libs.plugins.metro)
}

kotlin {
    jvm()
    android {
        namespace = "io.github.shadowrz.projectkafka.libraries.mediapickers.impl"
    }

    sourceSets {
        commonMain.dependencies {
            api(projects.libraries.mediapickers.api)
            implementation(projects.libraries.core)
            implementation(projects.libraries.di)
        }

        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
        }
    }
}
