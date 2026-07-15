plugins {
    alias(libs.plugins.projectkafka.compose)
    alias(libs.plugins.projectkafka.multiplatform)
    alias(libs.plugins.android.multiplatform)
}

kotlin {
    jvm()

    android {
        namespace = "io.github.shadowrz.projectkafka.libraries.architecture"
    }

    sourceSets {
        commonMain.dependencies {
            api(libs.compose.animation)
            api(libs.navigation3.runtime)
            api(project(":libraries:di"))
        }

        remove(commonTest.get())
    }
}
