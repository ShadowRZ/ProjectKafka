plugins {
    alias(libs.plugins.projectkafka.feature)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    jvm()
    android {
        namespace = "io.github.shadowrz.projectkafka.features.quickstart.impl"
    }

    sourceSets {
        commonMain.dependencies {
            api(projects.features.quickstart.api)
            implementation(projects.designsystem)
            implementation(projects.libraries.architecture)
            implementation(projects.libraries.kafkaui)
            implementation(projects.libraries.strings)
        }
    }
}

dependencies {
    add("kspAndroid", libs.hanekokoro.framework.codegen)
    add("kspJvm", libs.hanekokoro.framework.codegen)
}
