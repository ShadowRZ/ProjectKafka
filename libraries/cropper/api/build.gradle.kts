plugins {
    alias(libs.plugins.projectkafka.compose)
    alias(libs.plugins.projectkafka.multiplatform)
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
