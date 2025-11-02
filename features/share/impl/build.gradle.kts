plugins {
    id("io.github.shadowrz.projectkafka.compose-module")
    id("io.github.shadowrz.projectkafka.metro-module")
    id("io.github.shadowrz.projectkafka.multiplatform-module")
    id("io.github.shadowrz.projectkafka.library")
}

kotlin {
    android {
        namespace = "io.github.shadowrz.projectkafka.features.share.impl"

        androidResources {
            enable = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            api(projects.features.share.api)
            implementation(libs.hanekokoro.framework.runtime)
            implementation(projects.libraries.di)
            implementation(projects.libraries.icons)
        }

        androidMain.dependencies {
            implementation(projects.libraries.strings)
        }
    }
}
