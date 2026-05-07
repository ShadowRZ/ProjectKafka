plugins {
    alias(libs.plugins.projectkafka.compose)
    alias(libs.plugins.projectkafka.library)
}

android {
    namespace = "io.github.shadowrz.projectkafka.libraries.datepicker"
}

dependencies {
    api(project(":libraries:core"))
    implementation(project(":libraries:strings"))
}
