plugins {
    id("io.github.shadowrz.projectkafka.compose-module")
    id("io.github.shadowrz.projectkafka.metro-module")
    id("io.github.shadowrz.projectkafka.library")
}

android {
    namespace = "io.github.shadowrz.projectkafka.features.fronterindicator.impl"
}

dependencies {
    api(projects.features.fronterindicator.api)
    implementation(libs.androidx.activity.compose)
    implementation(projects.libraries.components)
    implementation(projects.libraries.core)
    implementation(projects.libraries.icons)
    implementation(projects.libraries.strings)
}
