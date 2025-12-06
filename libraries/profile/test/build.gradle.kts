plugins {
    id("io.github.shadowrz.projectkafka.multiplatform-module")
    id("io.github.shadowrz.projectkafka.compose-module")
    id("io.github.shadowrz.projectkafka.library")
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
