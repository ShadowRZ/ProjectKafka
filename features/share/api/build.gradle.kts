plugins {
    alias(libs.plugins.projectkafka.multiplatform)
}

kotlin {
    jvm()

    sourceSets {
        commonMain.dependencies {
            api(libs.uri)
            api(project(":libraries:architecture"))
        }

        remove(commonTest.get())
    }
}
