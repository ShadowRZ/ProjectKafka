plugins {
    id("io.github.shadowrz.projectkafka.multiplatform-module")
    id("io.github.shadowrz.projectkafka.metro-module")
    id("io.github.shadowrz.projectkafka.library")
}

kotlin {
    android {
        namespace = "io.github.shadowrz.projectkafka.libraries.featureflags.impl"
    }

    sourceSets {
        commonMain.dependencies {
            api(projects.libraries.featureflags.api)
        }
    }
}
