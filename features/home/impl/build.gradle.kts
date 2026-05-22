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
            api(project(":features:home:api"))
            implementation(libs.androidx.navigationevent.compose)
            implementation(libs.androidx.paging.compose)
            implementation(libs.circuit.sharedelements)
            implementation(libs.compose.material3)
            implementation(libs.composeunstyled.dialog)
            implementation(libs.decompose.compose)
            implementation(libs.decompose.compose.experimental)
            implementation(project(":designsystem"))
            implementation(project(":features:fronterindicator:api"))
            implementation(project(":features:profile:api"))
            implementation(project(":libraries:architecture"))
            implementation(project(":libraries:core"))
            implementation(project(":libraries:data:api"))
            implementation(project(":libraries:di"))
            implementation(project(":libraries:kafkaui"))
            implementation(project(":libraries:preferences:api"))
            implementation(project(":libraries:strings"))
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
