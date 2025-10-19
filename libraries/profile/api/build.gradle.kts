plugins {
    id("io.github.shadowrz.projectkafka.compose-module")
    id("io.github.shadowrz.projectkafka.library")
}

android {
    namespace = "io.github.shadowrz.projectkafka.libraries.profile.api"
}

dependencies {
    api(libs.krop.core)
    api(projects.libraries.mediapickers.api)
    implementation(projects.libraries.components)
    implementation(projects.libraries.crop)
    implementation(projects.libraries.icons)
}
