plugins {
    id("io.github.shadowrz.projectkafka.feature")
}

kotlin {
    android {
        namespace = "io.github.shadowrz.projectkafka.features.switchsystem.impl"
    }

    sourceSets {
        commonMain.dependencies {
            api(projects.features.switchsystem.api)
            implementation(libs.decompose.compose)
            implementation(libs.hanekokoro.framework.runtime)
            implementation(projects.features.createsystem.api)
            implementation(projects.libraries.core)
        }

        androidMain.dependencies {
            implementation(projects.libraries.components)
            implementation(projects.libraries.icons)
            implementation(projects.libraries.strings)
        }
    }
}
