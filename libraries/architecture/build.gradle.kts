plugins {
    id("io.github.shadowrz.projectkafka.compose")
    id("io.github.shadowrz.projectkafka.multiplatform")
    id("com.android.kotlin.multiplatform.library")
    alias(libs.plugins.metro)
}

kotlin {
    jvm()

    android {
        namespace = "io.github.shadowrz.projectkafka.libraries.architecture"
    }

    sourceSets {
        commonMain.dependencies {
            api(libs.decompose)
            api(libs.hanekokoro.framework.runtime)
            api(projects.libraries.di)
        }

        remove(commonTest.get())
    }
}
