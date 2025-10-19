plugins {
    id("io.github.shadowrz.projectkafka.compose-module")
    id("io.github.shadowrz.projectkafka.library")
}

android {
    namespace = "io.github.shadowrz.projectkafka.libraries.datepicker"
}

dependencies {
    api(projects.libraries.core)
    implementation(projects.libraries.strings)
}
