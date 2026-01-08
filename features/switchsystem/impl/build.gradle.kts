plugins {
    id("io.github.shadowrz.projectkafka.feature")
}

kotlin {
    jvm()
    android {
        namespace = "io.github.shadowrz.projectkafka.features.switchsystem.impl"
    }

    sourceSets {
        commonMain.dependencies {
            api(projects.features.switchsystem.api)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.decompose.compose)
            implementation(projects.features.createsystem.api)
            implementation(projects.libraries.core)
            implementation(projects.libraries.components)
            implementation(projects.libraries.icons)
            implementation(projects.libraries.strings)
        }
    }
}
