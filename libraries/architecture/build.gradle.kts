plugins {
    id("io.github.shadowrz.projectkafka.multiplatform-module")
    id("io.github.shadowrz.projectkafka.metro-module")
    id("io.github.shadowrz.projectkafka.library")
    id("io.github.shadowrz.projectkafka.compose-module")
}

kotlin {
    jvm()

    android {
        namespace = "io.github.shadowrz.projectkafka.libraries.architecture"
    }

    sourceSets {
        commonMain.dependencies {
            api(libs.decompose)
            api(libs.decompose.jetpack)
            api(projects.libraries.di)
        }

        remove(commonTest.get())
    }
}
