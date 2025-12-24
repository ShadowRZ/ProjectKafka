plugins {
    id("io.github.shadowrz.projectkafka.multiplatform")
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
