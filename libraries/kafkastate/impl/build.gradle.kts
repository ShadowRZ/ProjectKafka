plugins {
    alias(libs.plugins.projectkafka.multiplatform)
    alias(libs.plugins.projectkafka.compose)
    alias(libs.plugins.android.multiplatform)
    alias(libs.plugins.metro)
}

kotlin {
    jvm()
    android {
        namespace = "io.github.shadowrz.projectkafka.libraries.kafkastate.impl"
    }

    sourceSets {
        commonMain.dependencies {
            api(project(":libraries:kafkastate:api"))
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.hanekokoro.framework.markers)
            implementation(libs.hanekokoro.framework.runtime.presenter)
            implementation(project(":libraries:core"))
            implementation(project(":libraries:data:api"))
            implementation(project(":libraries:di"))
        }
    }
}
