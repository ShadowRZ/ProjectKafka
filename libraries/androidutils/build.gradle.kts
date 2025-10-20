plugins {
    id("io.github.shadowrz.projectkafka.library")
}

android {
    namespace = "io.github.shadowrz.projectkafka.libraries.androidutils"
}

dependencies {
    implementation(libs.androidx.browser)
    implementation(libs.androidx.core.ktx)
}
