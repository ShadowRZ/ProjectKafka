plugins {
    id("io.github.shadowrz.projectkafka.feature")
}

kotlin {
    android {
        namespace = "io.github.shadowrz.projectkafka.features.ftue.impl"

        androidResources {
            enable = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            api(projects.features.ftue.api)
            implementation(projects.libraries.di)
            implementation(projects.libraries.icons)
            implementation(projects.libraries.permissions.api)
            implementation(libs.accompanist.permissions)
            implementation(libs.decompose.compose)
            implementation(libs.essenty.coroutines)
        }

        androidMain.dependencies {
            implementation(projects.libraries.components)
            implementation(projects.libraries.strings)
            implementation(libs.accompanist.permissions)
        }
    }
}
