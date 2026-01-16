plugins {
    alias(libs.plugins.projectkafka.multiplatform)
    alias(libs.plugins.projectkafka.compose)
    alias(libs.plugins.android.multiplatform)
}

kotlin {
    jvm()
    android {
        namespace = "io.github.shadowrz.projectkafka.libraries.strings"

        // See https://youtrack.jetbrains.com/issue/CMP-8363
        androidResources {
            enable = true
        }
    }
}

compose.resources {
    publicResClass = true
    packageOfResClass = "io.github.shadowrz.projectkafka.libraries.strings"
    generateResClass = auto
}
