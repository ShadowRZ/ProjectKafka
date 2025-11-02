plugins {
    id("io.github.shadowrz.projectkafka.compose-module")
    id("io.github.shadowrz.projectkafka.metro-module")
    id("io.github.shadowrz.projectkafka.multiplatform-module")
    id("io.github.shadowrz.projectkafka.library")
}

kotlin {
    android {
        namespace = "io.github.shadowrz.projectkafka.features.about.impl"

        androidResources {
            enable = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            api(projects.features.about.api)
            implementation(projects.features.licenses.api)
            implementation(libs.decompose)
            implementation(libs.hanekokoro.framework.runtime)
        }
        androidMain.dependencies {
            implementation(projects.assets)
            implementation(projects.libraries.components)
            implementation(projects.libraries.icons)
            implementation(projects.libraries.strings)
        }

        remove(commonTest.get())
    }
}
