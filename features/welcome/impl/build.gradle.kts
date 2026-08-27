plugins {
    alias(libs.plugins.projectkafka.feature)
    alias(libs.plugins.projectkafka.kotest)
}

kotlin {
    jvm()
    android {
        namespace = "io.github.shadowrz.projectkafka.features.welcome.impl"
    }

    sourceSets {
        commonMain.dependencies {
            api(project(":features:welcome:api"))
            implementation(project(":designsystem"))
            implementation(project(":features:quickstart:api"))
            implementation(project(":libraries:architecture"))
            implementation(project(":libraries:di"))
            implementation(project(":libraries:strings"))
        }

        commonTest.dependencies {
            implementation(libs.compose.ui.test)
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
