plugins {
    alias(libs.plugins.projectkafka.feature)
}

kotlin {
    jvm()
    android {
        namespace = "io.github.shadowrz.projectkafka.features.welcome.impl"
    }

    sourceSets {
        commonMain.dependencies {
            api(projects.features.welcome.api)
            implementation(projects.designsystem)
            implementation(projects.features.createsystem.api)
            implementation(projects.libraries.architecture)
            implementation(projects.libraries.di)
            implementation(projects.libraries.strings)
        }

        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
        }
    }
}

dependencies {
    add("kspAndroid", libs.hanekokoro.framework.codegen)
    add("kspJvm", libs.hanekokoro.framework.codegen)
}
