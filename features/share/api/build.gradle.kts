plugins {
    alias(libs.plugins.projectkafka.multiplatform)
}

kotlin {
    jvm()

    sourceSets {
        commonMain.dependencies {
            api(project(":libraries:architecture"))
        }

        remove(commonTest.get())
    }
}
