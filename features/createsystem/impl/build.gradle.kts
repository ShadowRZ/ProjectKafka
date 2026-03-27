plugins {
    alias(libs.plugins.projectkafka.feature)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    jvm()
    android {
        namespace = "io.github.shadowrz.projectkafka.features.createsystem.impl"
    }

    sourceSets {
        commonMain.dependencies {
            api(projects.features.createsystem.api)
            implementation(libs.krop.core)
            implementation(libs.krop.ui)
            implementation(projects.designsystem)
            implementation(projects.libraries.architecture)
            implementation(projects.libraries.core)
            implementation(projects.libraries.cropper.api)
            implementation(projects.libraries.data.api)
            implementation(projects.libraries.kafkaui)
            implementation(projects.libraries.preferences.api)
            implementation(projects.libraries.strings)
        }
    }
}

dependencies {
    add("kspAndroid", libs.hanekokoro.framework.codegen)
    add("kspJvm", libs.hanekokoro.framework.codegen)
}
