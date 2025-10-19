plugins {
    id("io.github.shadowrz.projectkafka.compose-module")
    id("io.github.shadowrz.projectkafka.library")
}

android {
    namespace = "io.github.shadowrz.projectkafka.libraries.mediapickers.api"
}

dependencies {
    api(libs.androidx.activity.ktx)
    api(projects.libraries.core)
}
