plugins {
    alias(libs.plugins.projectkafka.multiplatform)
    alias(libs.plugins.projectkafka.compose)
    alias(libs.plugins.android.multiplatform)
}

kotlin {
    jvm()
    android {
        namespace = "io.github.shadowrz.projectkafka.libraries.localepicker"

        androidResources {
            enable = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            compileOnly(libs.compose.runtime)
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
