plugins {
    id("io.github.shadowrz.projectkafka.feature")
}

kotlin {
    jvm()
    android {
        namespace = "io.github.shadowrz.projectkafka.features.ftue.impl"
    }

    sourceSets {
        commonMain.dependencies {
            api(projects.features.ftue.api)
            implementation(projects.libraries.components)
            implementation(projects.libraries.di)
            implementation(projects.libraries.icons)
            implementation(projects.libraries.permissions.api)
            implementation(projects.libraries.strings)
            implementation(libs.decompose.compose)
            implementation(libs.essenty.coroutines)
        }

        androidMain.dependencies {
            implementation(libs.accompanist.permissions)
        }
    }
}
