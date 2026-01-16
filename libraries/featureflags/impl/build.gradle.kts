plugins {
    alias(libs.plugins.projectkafka.multiplatform)
    alias(libs.plugins.android.multiplatform)
    alias(libs.plugins.metro)
}

kotlin {
    jvm()
    android {
        namespace = "io.github.shadowrz.projectkafka.libraries.featureflags.impl"
    }

    sourceSets {
        commonMain.dependencies {
            api(projects.libraries.featureflags.api)
        }
    }
}
