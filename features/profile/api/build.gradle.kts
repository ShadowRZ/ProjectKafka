plugins {
    id("io.github.shadowrz.projectkafka.multiplatform")
}

kotlin {
    jvm()

    sourceSets {
        commonMain.dependencies {
            api(projects.libraries.architecture)
            api(projects.libraries.data.api)
        }

        remove(commonTest.get())
    }
}
