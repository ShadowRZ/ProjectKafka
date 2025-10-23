plugins {
    id("io.github.shadowrz.projectkafka.compose-module")
    id("io.github.shadowrz.projectkafka.multiplatform-module")
}

kotlin {
    jvm()

    sourceSets {
        commonMain.dependencies {
            api(projects.libraries.core)
        }

        remove(commonTest.get())
    }
}
