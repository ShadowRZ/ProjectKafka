plugins {
    id("io.github.shadowrz.projectkafka.multiplatform")
    id("io.github.shadowrz.projectkafka.compose")
    id("com.android.kotlin.multiplatform.library")
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
