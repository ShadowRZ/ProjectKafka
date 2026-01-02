plugins {
    id("io.github.shadowrz.projectkafka.multiplatform")
    id("com.android.kotlin.multiplatform.library")
    alias(libs.plugins.metro)
}

kotlin {
    jvm()
    android {
        namespace = "io.github.shadowrz.projectkafka.dependencies"
    }

    sourceSets {
        commonMain.dependencies {
            implementation(project.dependencies.platform(libs.androidx.compose.bom))
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
