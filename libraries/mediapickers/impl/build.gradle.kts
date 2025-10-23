plugins {
    id("io.github.shadowrz.projectkafka.compose-module")
    id("io.github.shadowrz.projectkafka.metro-module")
    id("io.github.shadowrz.projectkafka.multiplatform-module")
    id("io.github.shadowrz.projectkafka.library")
}

kotlin {
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
