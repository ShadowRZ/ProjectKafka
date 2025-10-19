plugins {
    id("io.github.shadowrz.projectkafka.compose-module")
    id("io.github.shadowrz.projectkafka.library")
}

android {
    namespace = "io.github.shadowrz.projectkafka.libraries.emojipicker"
}

dependencies {
    implementation(libs.androidx.emojipicker)
}
