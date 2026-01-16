plugins {
    alias(libs.plugins.projectkafka.multiplatform)
    alias(libs.plugins.projectkafka.compose)
    alias(libs.plugins.android.multiplatform)
    alias(libs.plugins.metro)
}

kotlin {
    jvm()
    android {
        namespace = "io.github.shadowrz.projectkafka.libraries.permissions.impl"
    }

    sourceSets {
        commonMain.dependencies {
            api(projects.libraries.permissions.api)
            implementation(libs.androidx.datastore.preferences)
            implementation(projects.libraries.di)
        }

        androidMain.dependencies {
            implementation(libs.accompanist.permissions)
            implementation(libs.androidx.core.ktx)
        }

        remove(commonTest.get())
    }
}
