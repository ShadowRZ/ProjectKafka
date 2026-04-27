plugins {
    alias(libs.plugins.projectkafka.feature)
}

kotlin {
    jvm()
    android {
        namespace = "io.github.shadowrz.projectkafka.features.switchsystem.impl"
    }

    sourceSets {
        commonMain.dependencies {
            api(project(":features:switchsystem:api"))
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.decompose.compose)
            implementation(project(":designsystem"))
            implementation(project(":features:createsystem:api"))
            implementation(project(":libraries:architecture"))
            implementation(project(":libraries:core"))
            implementation(project(":libraries:data:api"))
            implementation(project(":libraries:strings"))
        }
    }
}

dependencies {
    add("kspAndroid", libs.hanekokoro.framework.codegen)
    add("kspJvm", libs.hanekokoro.framework.codegen)
}
