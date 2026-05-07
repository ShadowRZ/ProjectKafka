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
            api(project(":features:createsystem:api"))
            implementation(libs.decompose.compose.experimental)
            implementation(libs.kermit)
            implementation(libs.krop.core)
            implementation(libs.krop.ui)
            implementation(project(":buildmeta"))
            implementation(project(":designsystem"))
            implementation(project(":libraries:architecture"))
            implementation(project(":libraries:core"))
            implementation(project(":libraries:cropper:api"))
            implementation(project(":libraries:data:api"))
            implementation(project(":libraries:kafkaui"))
            implementation(project(":libraries:preferences:api"))
            implementation(project(":libraries:strings"))
        }
    }
}

dependencies {
    add("kspAndroid", libs.hanekokoro.framework.codegen)
    add("kspJvm", libs.hanekokoro.framework.codegen)
}
