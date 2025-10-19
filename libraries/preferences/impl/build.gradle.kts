plugins {
    id("io.github.shadowrz.projectkafka.multiplatform-module")
    id("io.github.shadowrz.projectkafka.metro-module")
    id("io.github.shadowrz.projectkafka.library")
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
