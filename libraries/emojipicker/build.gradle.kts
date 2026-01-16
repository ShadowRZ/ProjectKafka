plugins {
    alias(libs.plugins.projectkafka.compose)
    alias(libs.plugins.projectkafka.library)
}

android {
    namespace = "io.github.shadowrz.projectkafka.libraries.emojipicker"
}

dependencies {
    implementation(libs.androidx.emojipicker)
}
