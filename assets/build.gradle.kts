plugins {
    id("io.github.shadowrz.projectkafka.multiplatform")
    id("io.github.shadowrz.projectkafka.compose")
    id("com.android.kotlin.multiplatform.library")
}

kotlin {
    jvm()
    android {
        namespace = "io.github.shadowrz.projectkafka.assets"

        // For Android App Icons
        androidResources {
            enable = true
        }
    }
}

compose.resources {
    publicResClass = true
    packageOfResClass = "io.github.shadowrz.projectkafka.assets"
    generateResClass = auto
}
