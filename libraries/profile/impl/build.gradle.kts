plugins {
    id("io.github.shadowrz.projectkafka.compose-module")
    id("io.github.shadowrz.projectkafka.library")
    id("io.github.shadowrz.projectkafka.metro-module")
}

android {
    namespace = "io.github.shadowrz.projectkafka.libraries.profile.impl"
}

dependencies {
    api(projects.libraries.profile.api)
    implementation(libs.okio)
    implementation(projects.libraries.core)
    implementation(projects.libraries.di)
}
