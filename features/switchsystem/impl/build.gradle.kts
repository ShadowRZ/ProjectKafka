plugins {
    id("io.github.shadowrz.projectkafka.compose-module")
    id("io.github.shadowrz.projectkafka.metro-module")
    id("io.github.shadowrz.projectkafka.multiplatform-module")
    id("io.github.shadowrz.projectkafka.library")
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
