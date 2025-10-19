plugins {
    id("io.github.shadowrz.projectkafka.multiplatform-module")
    id("io.github.shadowrz.projectkafka.compose-module")
    id("io.github.shadowrz.projectkafka.metro-module")
    id("io.github.shadowrz.projectkafka.library")
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
