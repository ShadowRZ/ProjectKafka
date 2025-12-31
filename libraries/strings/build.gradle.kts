plugins {
    id("io.github.shadowrz.projectkafka.multiplatform")
    id("io.github.shadowrz.projectkafka.compose")
    id("com.android.kotlin.multiplatform.library")
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
