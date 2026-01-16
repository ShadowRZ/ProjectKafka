plugins {
    alias(libs.plugins.projectkafka.multiplatform)
    alias(libs.plugins.projectkafka.compose)
    alias(libs.plugins.android.multiplatform)
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
