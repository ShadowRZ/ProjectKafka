plugins {
    id("io.github.shadowrz.projectkafka.library")
    id("io.github.shadowrz.projectkafka.metro-module")
}

android {
    namespace = "io.github.shadowrz.projectkafka.libraries.featureflags.impl"
}

dependencies {
    api(projects.libraries.featureflags.api)
}
