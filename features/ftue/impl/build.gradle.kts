plugins {
    alias(libs.plugins.projectkafka.feature)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    jvm()
    android {
        namespace = "io.github.shadowrz.projectkafka.features.ftue.impl"
    }

    sourceSets {
        commonMain.dependencies {
            api(project(":features:ftue:api"))
            implementation(libs.navigation3.ui)
            implementation(project(":designsystem"))
            implementation(project(":libraries:architecture"))
            implementation(project(":libraries:core"))
            implementation(project(":libraries:di"))
            implementation(project(":libraries:permissions:api"))
            implementation(project(":libraries:strings"))
        }

        androidMain.dependencies {
            implementation(libs.accompanist.permissions)
        }
    }
}
