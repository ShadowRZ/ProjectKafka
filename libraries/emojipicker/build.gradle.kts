plugins {
    id("io.github.shadowrz.projectkafka.compose")
    id("io.github.shadowrz.projectkafka.library")
}

android {
    namespace = "io.github.shadowrz.projectkafka.libraries.emojipicker"
}

dependencies {
    implementation(libs.androidx.emojipicker)
}
