plugins {
    alias(libs.plugins.projectkafka.feature)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    jvm()
    android {
        namespace = "io.github.shadowrz.projectkafka.features.datamanage.impl"

        androidResources {
            enable = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            api(projects.features.datamanage.api)
            implementation(libs.okio)
            implementation(libs.sqldelight.runtime)
            implementation(projects.assets)
            implementation(projects.libraries.architecture)
            implementation(projects.libraries.components)
            implementation(projects.libraries.core)
            implementation(projects.libraries.data.api)
            implementation(projects.libraries.di)
            implementation(projects.libraries.icons)
            implementation(projects.libraries.mediapickers.api)
            implementation(projects.libraries.strings)
            implementation(projects.libraries.systemgraph)
            implementation(projects.libraries.zipwriter)
        }
        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
        }

        remove(commonTest.get())
    }
}

dependencies {
    add("kspAndroid", libs.hanekokoro.framework.codegen)
    add("kspJvm", libs.hanekokoro.framework.codegen)
}
