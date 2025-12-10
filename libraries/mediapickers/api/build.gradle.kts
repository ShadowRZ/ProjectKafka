plugins {
    id("io.github.shadowrz.projectkafka.compose")
    id("io.github.shadowrz.projectkafka.multiplatform")
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
