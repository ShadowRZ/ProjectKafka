plugins {
    id("io.github.shadowrz.projectkafka.compose")
    id("io.github.shadowrz.projectkafka.multiplatform")
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
