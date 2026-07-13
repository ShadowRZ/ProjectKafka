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
            implementation(libs.composeunstyled.dialog)
            implementation(libs.hanekokoro.framework.runtime.retain)
            implementation(libs.navigation3.ui)
            implementation(project(":designsystem"))
            implementation(project(":features:about:api"))
            implementation(project(":features:datamanage:api"))
            implementation(project(":features:editmember:api"))
            implementation(project(":features:fronterindicator:api"))
            implementation(project(":features:messages:api"))
            implementation(project(":features:preferences:api"))
            implementation(project(":features:profile:api"))
            implementation(project(":features:switchsystem:api"))
            implementation(project(":libraries:architecture"))
            implementation(project(":libraries:core"))
            implementation(project(":libraries:data:api"))
            implementation(project(":libraries:di"))
            implementation(project(":libraries:kafkastate:api"))
            implementation(project(":libraries:kafkaui"))
            implementation(project(":libraries:preferences:api"))
            implementation(project(":libraries:strings"))
        }

        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
            implementation(libs.androidx.core)
        }
    }
}
