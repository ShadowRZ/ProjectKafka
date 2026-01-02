plugins {
    id("io.github.shadowrz.projectkafka.multiplatform")
    id("com.android.kotlin.multiplatform.library")
    alias(libs.plugins.metro)
}

kotlin {
    jvm()
    android {
        namespace = "io.github.shadowrz.projectkafka.libraries.systemgraph"
    }

    sourceSets {
        commonMain.dependencies {
            api(projects.libraries.data.api)
        }

        remove(commonTest.get())
    }
}
