import io.github.shadowrz.projectkafka.gradle.plugins.extensions.allFeaturesApi

plugins {
    alias(libs.plugins.projectkafka.compose)
    alias(libs.plugins.projectkafka.multiplatform)
    alias(libs.plugins.android.multiplatform)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.metro)
}

kotlin {
    jvm()
    android {
        namespace = "io.github.shadowrz.projectkafka.compose"

        androidResources {
            enable = false
        }
    }

    sourceSets {
        commonMain.dependencies {
            // Add All features API
            allFeaturesApi(project)
            implementation(libs.circuit.sharedelements)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.ui)
            implementation(libs.decompose)
            implementation(libs.hanekokoro.framework.annotations)
            implementation(libs.hanekokoro.framework.integration)
            implementation(libs.hanekokoro.framework.runtime.component)
            implementation(libs.decompose.compose.experimental)
            implementation(libs.essenty.coroutines)
            implementation(libs.kermit)
            implementation(libs.okio)
            implementation(projects.designsystem)
            implementation(projects.libraries.architecture)
            implementation(projects.libraries.core)
            implementation(projects.libraries.data.api)
            implementation(projects.libraries.di)
            implementation(projects.libraries.preferences.api)
            implementation(projects.libraries.systemgraph)
        }

        remove(commonTest.get())
    }
}

dependencies {
    add("kspAndroid", libs.hanekokoro.framework.codegen)
    add("kspJvm", libs.hanekokoro.framework.codegen)
}
