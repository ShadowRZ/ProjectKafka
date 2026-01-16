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
            api(projects.libraries.preferences.api)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.androidx.datastore.preferences)
            implementation(projects.libraries.data.api)
            implementation(projects.libraries.di)
        }

        jvmMain.dependencies {
            // Used to get ProjectDirectories for preferencesDir
            implementation(libs.directories)
        }

        remove(commonTest.get())
    }
}
