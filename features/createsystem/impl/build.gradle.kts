plugins {
    alias(libs.plugins.projectkafka.feature)
    alias(libs.plugins.projectkafka.kotest)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    jvm()
    android {
        namespace = "io.github.shadowrz.projectkafka.features.createsystem.impl"
    }

    sourceSets {
        commonMain.dependencies {
            api(project(":features:createsystem:api"))
            implementation(libs.kermit)
            implementation(libs.krop.core)
            implementation(libs.krop.ui)
            implementation(libs.navigation3.ui)
            implementation(project(":buildmeta"))
            implementation(project(":designsystem"))
            implementation(project(":libraries:architecture"))
            implementation(project(":libraries:core"))
            implementation(project(":libraries:cropper:api"))
            implementation(project(":libraries:data:api"))
            implementation(project(":libraries:kafkaui"))
            implementation(project(":libraries:preferences:api"))
            implementation(project(":libraries:resultevents"))
            implementation(project(":libraries:strings"))
        }

        commonTest.dependencies {
            implementation(libs.kotlinx.coroutines.test)
            implementation(libs.molecule.runtime)
            implementation(libs.turbine)
            implementation(project(":tests:utils"))
        }

        jvmTest.dependencies {
            // Compose
            implementation(compose.desktop.currentOs)
        }
    }
}
