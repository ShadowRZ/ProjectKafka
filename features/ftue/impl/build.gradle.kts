plugins {
    alias(libs.plugins.projectkafka.feature)
}

kotlin {
    jvm()
    android {
        namespace = "io.github.shadowrz.projectkafka.features.ftue.impl"
    }

    sourceSets {
        commonMain.dependencies {
            api(projects.features.ftue.api)
            implementation(projects.libraries.architecture)
            implementation(projects.libraries.components)
            implementation(projects.libraries.di)
            implementation(projects.libraries.icons)
            implementation(projects.libraries.permissions.api)
            implementation(projects.libraries.strings)
            implementation(libs.decompose.compose)
            implementation(libs.essenty.coroutines)
        }

        androidMain.dependencies {
            implementation(libs.accompanist.permissions)
        }
    }
}

dependencies {
    add("kspAndroid", libs.hanekokoro.framework.codegen)
    add("kspJvm", libs.hanekokoro.framework.codegen)
}
