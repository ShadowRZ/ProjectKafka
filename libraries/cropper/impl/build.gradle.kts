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
            implementation(libs.compose.ui)
            implementation(libs.filekit.dialogs.compose)
            implementation(libs.hanekokoro.framework.runtime.retain)
            implementation(libs.krop.core)
            implementation(libs.krop.filekit)
            implementation(libs.okio)
            implementation(project(":libraries:core"))
            implementation(project(":libraries:cropper:api"))
            implementation(project(":libraries:fileutils"))
            implementation(project(":libraries:di"))
        }

        androidMain.dependencies {
            implementation(libs.androidx.core.ktx)
        }
    }
}
