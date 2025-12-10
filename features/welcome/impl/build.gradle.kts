plugins {
    id("io.github.shadowrz.projectkafka.feature")
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
