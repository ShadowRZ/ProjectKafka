plugins {
    alias(libs.plugins.projectkafka.multiplatform)
    alias(libs.plugins.android.multiplatform)
    alias(libs.plugins.metro)
}

kotlin {
    jvm()
    android {
        namespace = "io.github.shadowrz.projectkafka.libraries.preferences.impl"
    }

    sourceSets {
        commonMain.dependencies {
            api(project(":libraries:preferences:api"))
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.androidx.datastore.preferences)
            implementation(project(":libraries:data:api"))
            implementation(project(":libraries:di"))
        }

        jvmMain.dependencies {
            // Used to get ProjectDirectories for preferencesDir
            implementation(libs.directories)
        }

        remove(commonTest.get())
    }
}
