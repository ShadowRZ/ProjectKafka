plugins {
    alias(libs.plugins.projectkafka.feature)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    jvm()
    android {
        namespace = "io.github.shadowrz.projectkafka.features.home.impl"
    }

    sourceSets {
        commonMain.dependencies {
            api(projects.features.home.api)
            implementation(libs.androidx.paging.compose)
            implementation(libs.circuit.sharedelements)
            implementation(libs.compose.material3)
            implementation(libs.composeunstyled.primitives)
            implementation(libs.decompose.compose)
            implementation(libs.decompose.compose.experimental)
            implementation(projects.designsystem)
            implementation(projects.features.fronterindicator.api)
            implementation(projects.features.profile.api)
            implementation(projects.libraries.architecture)
            implementation(projects.libraries.core)
            implementation(projects.libraries.data.api)
            implementation(projects.libraries.di)
            implementation(projects.libraries.kafkaui)
            implementation(projects.libraries.preferences.api)
            implementation(projects.libraries.strings)
        }

        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
            implementation(libs.androidx.core.ktx)
        }
    }
}

dependencies {
    add("kspAndroid", libs.hanekokoro.framework.codegen)
    add("kspJvm", libs.hanekokoro.framework.codegen)
}
