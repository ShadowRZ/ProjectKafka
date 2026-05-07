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
            implementation(project(":designsystem"))
            implementation(project(":libraries:architecture"))
            implementation(project(":libraries:core"))
            implementation(project(":libraries:data:api"))
            implementation(project(":libraries:di"))
            implementation(project(":libraries:preferences:api"))
            implementation(project(":libraries:systemgraph"))
            // Add All features API
            implementation(project(":features:about:api"))
            implementation(project(":features:createsystem:api"))
            implementation(project(":features:datamanage:api"))
            implementation(project(":features:editmember:api"))
            implementation(project(":features:fronterindicator:api"))
            implementation(project(":features:frontlog:api"))
            implementation(project(":features:ftue:api"))
            implementation(project(":features:home:api"))
            implementation(project(":features:licenses:api"))
            implementation(project(":features:preferences:api"))
            implementation(project(":features:profile:api"))
            implementation(project(":features:quickstart:api"))
            implementation(project(":features:share:api"))
            implementation(project(":features:switchsystem:api"))
            implementation(project(":features:welcome:api"))
        }

        remove(commonTest.get())
    }
}

dependencies {
    add("kspAndroid", libs.hanekokoro.framework.codegen)
    add("kspJvm", libs.hanekokoro.framework.codegen)
}
