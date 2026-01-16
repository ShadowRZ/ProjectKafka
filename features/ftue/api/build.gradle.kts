plugins {
    alias(libs.plugins.projectkafka.multiplatform)
}

kotlin {
    jvm()

    sourceSets {
        commonMain.dependencies {
            api(projects.libraries.architecture)
            implementation(libs.kotlinx.coroutines.core)
        }

        remove(commonTest.get())
    }
}
