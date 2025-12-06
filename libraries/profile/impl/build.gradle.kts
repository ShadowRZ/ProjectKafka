plugins {
    id("io.github.shadowrz.projectkafka.multiplatform-module")
    id("io.github.shadowrz.projectkafka.compose-module")
    id("io.github.shadowrz.projectkafka.library")
    id("io.github.shadowrz.projectkafka.metro-module")
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
