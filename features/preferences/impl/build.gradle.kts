plugins {
    id("io.github.shadowrz.projectkafka.feature")
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
            implementation(libs.decompose.compose.experimental)
            implementation(projects.libraries.components)
            implementation(projects.libraries.icons)
            implementation(projects.libraries.preferences.api)
            implementation(projects.libraries.strings)
        }
    }
}

dependencies {
    add("kspAndroid", libs.hanekokoro.framework.codegen)
    add("kspJvm", libs.hanekokoro.framework.codegen)
}
