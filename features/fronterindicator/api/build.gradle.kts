plugins {
    id("io.github.shadowrz.projectkafka.multiplatform")
    id("io.github.shadowrz.projectkafka.compose")
}

kotlin {
    sourceSets {
        jvm()
        commonMain.dependencies {
            api(projects.libraries.architecture)
        }

        remove(commonTest.get())
    }
}
