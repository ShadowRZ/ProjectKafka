plugins {
    id("io.github.shadowrz.projectkafka.multiplatform")
    id("io.github.shadowrz.projectkafka.compose")
    id("com.android.kotlin.multiplatform.library")
    alias(libs.plugins.metro)
}

kotlin {
    android {
        namespace = "io.github.shadowrz.projectkafka.libraries.permissions.impl"
    }

    sourceSets {
        commonMain.dependencies {
            api(projects.libraries.permissions.api)
            implementation(libs.accompanist.permissions)
            implementation(projects.libraries.di)
        }

        androidMain.dependencies {
            implementation(libs.androidx.core.ktx)
            implementation(libs.androidx.datastore.preferences)
        }

        remove(commonTest.get())
    }
}
