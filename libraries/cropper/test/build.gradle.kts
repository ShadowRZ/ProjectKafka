plugins {
    alias(libs.plugins.projectkafka.multiplatform)
    alias(libs.plugins.projectkafka.compose)
    alias(libs.plugins.android.multiplatform)
}

kotlin {
    jvm()
    android {
        namespace = "io.github.shadowrz.projectkafka.libraries.cropper.test"
    }

    sourceSets {
        commonMain.dependencies {
            api(projects.libraries.cropper.api)
            implementation(libs.krop.core)
        }
    }
}
