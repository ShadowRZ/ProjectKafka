plugins {
    id("io.github.shadowrz.projectkafka.compose-module")
    id("io.github.shadowrz.projectkafka.library")
}

android {
    namespace = "io.github.shadowrz.projectkafka.libraries.profile.test"
}

dependencies {
    api(projects.libraries.profile.api)
    implementation(projects.libraries.components)
    implementation(projects.libraries.crop)
}
