plugins {
    alias(libs.plugins.projectkafka.multiplatform)
    alias(libs.plugins.projectkafka.compose)
    alias(libs.plugins.android.multiplatform)
}

kotlin {
    jvm()
    androidLibrary {
        namespace = "io.github.shadowrz.projectkafka.libraries.profile.test"
    }

    sourceSets {
        commonMain.dependencies {
            api(projects.libraries.profile.api)
            implementation(projects.libraries.crop)
            implementation(libs.krop.core)
        }
    }
}
