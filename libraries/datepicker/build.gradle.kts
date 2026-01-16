plugins {
    alias(libs.plugins.projectkafka.compose)
    alias(libs.plugins.projectkafka.library)
}

android {
    namespace = "io.github.shadowrz.projectkafka.libraries.datepicker"
}

dependencies {
    api(projects.libraries.core)
    implementation(projects.libraries.strings)
}
