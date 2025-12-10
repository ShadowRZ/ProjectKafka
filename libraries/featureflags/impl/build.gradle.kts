plugins {
    id("io.github.shadowrz.projectkafka.multiplatform")
    id("com.android.kotlin.multiplatform.library")
    alias(libs.plugins.metro)
}

kotlin {
    android {
        namespace = "io.github.shadowrz.projectkafka.libraries.featureflags.impl"
    }

    sourceSets {
        commonMain.dependencies {
            api(projects.libraries.featureflags.api)
        }
    }
}
