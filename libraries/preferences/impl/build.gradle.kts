plugins {
    id("io.github.shadowrz.projectkafka.multiplatform")
    id("com.android.kotlin.multiplatform.library")
    alias(libs.plugins.metro)
}

kotlin {
    android {
        namespace = "io.github.shadowrz.projectkafka.libraries.preferences.impl"
    }

    sourceSets {
        commonMain.dependencies {
            api(projects.libraries.preferences.api)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.androidx.datastore.preferences)
            implementation(projects.libraries.di)
        }

        remove(commonTest.get())
    }
}
