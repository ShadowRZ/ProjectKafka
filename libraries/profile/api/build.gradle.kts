plugins {
    id("io.github.shadowrz.projectkafka.compose-module")
    id("io.github.shadowrz.projectkafka.multiplatform-module")
}

kotlin {
    jvm()

    sourceSets {
        commonMain.dependencies {
            api(libs.krop.core)
            api(projects.libraries.mediapickers.api)
        }

        remove(commonTest.get())
    }
}
