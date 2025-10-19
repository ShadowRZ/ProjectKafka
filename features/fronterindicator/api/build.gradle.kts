plugins {
    id("io.github.shadowrz.projectkafka.multiplatform-module")
    id("io.github.shadowrz.projectkafka.compose-module")
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
