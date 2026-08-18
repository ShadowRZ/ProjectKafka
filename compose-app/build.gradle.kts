plugins {
    alias(libs.plugins.projectkafka.compose)
    alias(libs.plugins.projectkafka.multiplatform)
    alias(libs.plugins.android.multiplatform)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.metro)
    alias(libs.plugins.stability.analyzer)
}

kotlin {
    jvm()
    android {
        namespace = "io.github.shadowrz.projectkafka.compose"

        androidResources {
            enable = false
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.compose.foundation)
            implementation(libs.compose.ui)
            implementation(libs.hanekokoro.framework.runtime.coroutines)
            implementation(libs.kermit)
            implementation(libs.okio)
            implementation(libs.navigation3.ui)
            implementation(project(":designsystem"))
            implementation(project(":libraries:architecture"))
            implementation(project(":libraries:core"))
            implementation(project(":libraries:data:api"))
            implementation(project(":libraries:di"))
            implementation(project(":libraries:preferences:api"))
            implementation(project(":libraries:resultevents"))
            implementation(project(":libraries:systemgraph"))
            implementation(project(":libraries:uniqueid"))
            // Add All features API
            implementation(project(":features:about:api"))
            implementation(project(":features:createsystem:api"))
            implementation(project(":features:datamanage:api"))
            implementation(project(":features:editmember:api"))
            implementation(project(":features:fronterindicator:api"))
            implementation(project(":features:frontlog:api"))
            implementation(project(":features:ftue:api"))
            implementation(project(":features:home:api"))
            implementation(project(":features:licenses:api"))
            implementation(project(":features:messages:api"))
            implementation(project(":features:preferences:api"))
            implementation(project(":features:profile:api"))
            implementation(project(":features:quickstart:api"))
            implementation(project(":features:share:api"))
            implementation(project(":features:switchsystem:api"))
            implementation(project(":features:welcome:api"))
        }

        remove(commonTest.get())
    }
}

configurations.configureEach {
    exclude(group = "com.arkivanov.essenty")
}

@Suppress("UnstableApiUsage")
composeStabilityAnalyzer {
    enabled = true

    stabilityConfigurationFiles.add(isolated.rootProject.projectDirectory.file("config/compose/compose.conf"))

    stabilityValidation {
        enabled = true
    }
}
