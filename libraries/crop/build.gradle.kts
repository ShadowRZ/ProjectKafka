plugins {
    id("io.github.shadowrz.projectkafka.multiplatform")
    id("io.github.shadowrz.projectkafka.compose")
    id("com.android.kotlin.multiplatform.library")
}

kotlin {
    jvm()
    android {
        namespace = "io.github.shadowrz.projectkafka.libraries.crop"
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.krop.core)
            implementation(projects.libraries.icons)
        }

        remove(commonTest.get())
    }
}
