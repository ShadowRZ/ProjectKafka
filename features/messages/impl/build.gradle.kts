plugins {
    alias(libs.plugins.projectkafka.feature)
    alias(libs.plugins.projectkafka.kotest)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    jvm()
    android {
        namespace = "io.github.shadowrz.projectkafka.features.messages.impl"
    }

    sourceSets {
        commonMain.dependencies {
            api(project(":features:messages:api"))
            implementation(libs.androidx.paging.compose)
            implementation(libs.hanekokoro.framework.runtime.retain)
            implementation(project(":designsystem"))
            implementation(project(":libraries:architecture"))
            implementation(project(":libraries:core"))
            implementation(project(":libraries:data:api"))
            implementation(project(":libraries:kafkastate:api"))
            implementation(project(":libraries:kafkaui"))
            implementation(project(":libraries:richeditor"))
            implementation(project(":libraries:strings"))
        }

        commonTest.dependencies {
            implementation(libs.kotlinx.coroutines.test)
            implementation(libs.molecule.runtime)
            implementation(libs.turbine)
            implementation(project(":tests:utils"))
        }

        jvmTest.dependencies {
            implementation(libs.kotest.runner.junit6)
        }
    }
}
