plugins {
    id("io.github.shadowrz.projectkafka.compose-module")
    id("io.github.shadowrz.projectkafka.multiplatform-module")
    id("io.github.shadowrz.projectkafka.library")
}

kotlin {
    android {
        namespace = "io.github.shadowrz.projectkafka.libraries.profile.components"

        androidResources {
            enable = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            api(projects.libraries.profile.api)
            implementation(libs.krop.core)
            implementation(projects.libraries.components)
            implementation(projects.libraries.crop)
            implementation(projects.libraries.icons)
        }

        remove(commonTest.get())
    }
}
