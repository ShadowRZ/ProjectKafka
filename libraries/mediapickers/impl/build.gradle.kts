plugins {
    id("io.github.shadowrz.projectkafka.compose-module")
    id("io.github.shadowrz.projectkafka.metro-module")
    id("io.github.shadowrz.projectkafka.library")
}

android {
    namespace = "io.github.shadowrz.projectkafka.libraries.mediapickers.impl"
}

dependencies {
    api(projects.libraries.mediapickers.api)
    implementation(libs.androidx.activity.compose)
    implementation(projects.libraries.core)
    implementation(projects.libraries.di)
}
