plugins {
    alias(libs.plugins.projectkafka.multiplatform)
    alias(libs.plugins.projectkafka.compose)
    alias(libs.plugins.android.multiplatform)
}

kotlin {
    jvm()
    android {
        namespace = "io.github.shadowrz.projectkafka.libraries.crop"
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.krop.core)
            implementation(projects.libraries.icons)
        }

        remove(commonTest.get())
    }
}
