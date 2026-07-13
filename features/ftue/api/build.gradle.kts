plugins {
    alias(libs.plugins.projectkafka.multiplatform)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    jvm()

    sourceSets {
        commonMain.dependencies {
            api(project(":libraries:architecture"))
            implementation(libs.kotlinx.coroutines.core)
        }

        remove(commonTest.get())
    }
}
