plugins {
    id("io.github.shadowrz.projectkafka.compose-module")
    id("io.github.shadowrz.projectkafka.metro-module")
    id("io.github.shadowrz.projectkafka.multiplatform-module")
    id("io.github.shadowrz.projectkafka.library")
}

kotlin {
    android {
        namespace = "io.github.shadowrz.projectkafka.features.welcome.impl"

        androidResources {
            enable = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            api(projects.features.welcome.api)
            implementation(libs.hanekokoro.framework.runtime)
            implementation(projects.features.createsystem.api)
            implementation(projects.libraries.di)
            implementation(projects.libraries.icons)
            implementation(projects.libraries.localepicker)
        }

        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
            implementation(libs.androidx.appcompat)
            implementation(projects.assets)
            implementation(projects.libraries.androidutils)
            implementation(projects.libraries.components)
            implementation(projects.libraries.strings)
        }
    }
}
