plugins {
    id("io.github.shadowrz.projectkafka.compose")
    id("io.github.shadowrz.projectkafka.multiplatform")
    id("com.android.kotlin.multiplatform.library")
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
