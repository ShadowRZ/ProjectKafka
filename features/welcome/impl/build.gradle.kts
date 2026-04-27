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
            api(project(":features:welcome:api"))
            implementation(project(":designsystem"))
            implementation(project(":features:createsystem:api"))
            implementation(project(":libraries:architecture"))
            implementation(project(":libraries:di"))
            implementation(project(":libraries:strings"))
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
