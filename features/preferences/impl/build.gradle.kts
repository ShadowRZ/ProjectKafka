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
            api(projects.features.preferences.api)
            implementation(libs.circuit.sharedelements)
            implementation(projects.designsystem)
            implementation(projects.libraries.architecture)
            implementation(projects.libraries.preferences.api)
            implementation(projects.libraries.strings)
        }
    }
}

dependencies {
    add("kspAndroid", libs.hanekokoro.framework.codegen)
    add("kspJvm", libs.hanekokoro.framework.codegen)
}
