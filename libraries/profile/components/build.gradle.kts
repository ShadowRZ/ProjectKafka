plugins {
    id("io.github.shadowrz.projectkafka.compose")
    id("io.github.shadowrz.projectkafka.multiplatform")
    id("com.android.kotlin.multiplatform.library")
}

kotlin {
    jvm()
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
