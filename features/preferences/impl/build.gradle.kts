plugins {
    id("io.github.shadowrz.projectkafka.compose-module")
    id("io.github.shadowrz.projectkafka.metro-module")
    id("io.github.shadowrz.projectkafka.multiplatform-module")
    id("io.github.shadowrz.projectkafka.library")
}

kotlin {
    android {
        namespace = "io.github.shadowrz.projectkafka.features.preferences.impl"

        androidResources {
            enable = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            api(projects.features.preferences.api)
            implementation(libs.hanekokoro.framework.runtime)
        }
    }
}
