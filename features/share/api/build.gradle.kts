plugins {
    id("io.github.shadowrz.projectkafka.multiplatform")
}

kotlin {
    jvm()

    sourceSets {
        commonMain.dependencies {
            api(libs.uri)
            api(projects.libraries.architecture)
        }

        remove(commonTest.get())
    }
}
