plugins {
    alias(libs.plugins.projectkafka.multiplatform)
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
