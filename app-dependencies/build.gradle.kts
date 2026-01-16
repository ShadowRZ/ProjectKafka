plugins {
    alias(libs.plugins.projectkafka.multiplatform)
    alias(libs.plugins.android.multiplatform)
    alias(libs.plugins.metro)
}

kotlin {
    jvm()
    android {
        namespace = "io.github.shadowrz.projectkafka.dependencies"
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.hanekokoro.framework.integration)
            implementation(libs.hanekokoro.framework.runtime)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.okio)
            implementation(projects.libraries.core)
            implementation(projects.libraries.data.api)
            implementation(projects.libraries.di)
            implementation(projects.libraries.systemgraph)
        }
    }
}
