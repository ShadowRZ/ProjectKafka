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
            api(project(":features:ftue:api"))
            implementation(project(":designsystem"))
            implementation(project(":libraries:architecture"))
            implementation(project(":libraries:core"))
            implementation(project(":libraries:di"))
            implementation(project(":libraries:permissions:api"))
            implementation(project(":libraries:strings"))
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
