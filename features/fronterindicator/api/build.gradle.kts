plugins {
    alias(libs.plugins.projectkafka.multiplatform)
    alias(libs.plugins.projectkafka.compose)
}

kotlin {
    jvm()

    sourceSets {
        commonMain.dependencies {
            api(projects.libraries.architecture)
        }

        remove(commonTest.get())
    }
}
