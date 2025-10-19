plugins {
    alias(libs.plugins.kotlin.multiplatform)
}

kotlin {
    sourceSets {
        jvm()
        commonMain.dependencies {
            api(libs.uri)
            api(projects.libraries.architecture)
        }

        remove(commonTest.get())
    }
}
