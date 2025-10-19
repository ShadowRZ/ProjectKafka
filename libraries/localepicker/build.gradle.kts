plugins {
    id("io.github.shadowrz.projectkafka.multiplatform-module")
    id("io.github.shadowrz.projectkafka.compose-module")
    id("io.github.shadowrz.projectkafka.library")
}

kotlin {
    android {
        namespace = "io.github.shadowrz.projectkafka.libraries.localepicker"

        androidResources {
            enable = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            compileOnly(libs.androidx.compose.annotation)
        }

        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
            implementation(libs.androidx.appcompat)
            implementation(libs.androidx.core.ktx)
            implementation(projects.libraries.androidutils)
            implementation(projects.libraries.core)
            implementation(projects.libraries.strings)
        }

        remove(commonTest.get())
    }
}
