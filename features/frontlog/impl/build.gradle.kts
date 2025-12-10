plugins {
    id("io.github.shadowrz.projectkafka.feature")
}

kotlin {
    android {
        namespace = "io.github.shadowrz.projectkafka.features.frontlog.impl"
    }

    sourceSets {
        commonMain.dependencies {
            api(projects.features.frontlog.api)
            implementation(libs.decompose.compose)
            implementation(libs.hanekokoro.framework.runtime)
            implementation(projects.libraries.core)
            implementation(projects.libraries.data.api)
        }

        androidMain.dependencies {
            implementation(projects.libraries.components)
            implementation(projects.libraries.strings)
        }
    }
}
