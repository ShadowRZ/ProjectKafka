plugins {
    alias(libs.plugins.projectkafka.compose)
    alias(libs.plugins.projectkafka.multiplatform)
    alias(libs.plugins.android.multiplatform)
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
