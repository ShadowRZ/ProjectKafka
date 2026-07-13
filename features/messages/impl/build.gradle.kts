plugins {
    alias(libs.plugins.projectkafka.feature)
    alias(libs.plugins.projectkafka.kotest)
}

kotlin {
    jvm()
    android {
        namespace = "io.github.shadowrz.projectkafka.features.messsages.impl"
    }

    sourceSets {
        commonMain.dependencies {
            api(project(":features:messages:api"))
            implementation(libs.androidx.paging.compose)
            implementation(libs.hanekokoro.framework.runtime.retain)
            implementation(libs.navigation3.adaptive)
            implementation(project(":designsystem"))
            implementation(project(":libraries:architecture"))
            implementation(project(":libraries:core"))
            implementation(project(":libraries:data:api"))
            implementation(project(":libraries:kafkastate:api"))
            implementation(project(":libraries:kafkaui"))
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
