plugins {
    alias(libs.plugins.projectkafka.feature)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    jvm()
    android {
        namespace = "io.github.shadowrz.projectkafka.features.preferences.impl"
    }

    sourceSets {
        commonMain.dependencies {
            api(project(":features:preferences:api"))
            implementation(libs.circuit.sharedelements)
            implementation(libs.navigation3.ui)
            implementation(project(":designsystem"))
            implementation(project(":features:datamanage:api"))
            implementation(project(":libraries:architecture"))
            implementation(project(":libraries:preferences:api"))
            implementation(project(":libraries:strings"))
        }
    }
}
