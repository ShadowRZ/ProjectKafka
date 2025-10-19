plugins {
    alias(libs.plugins.kotlin.multiplatform)
}

kotlin {
    sourceSets {
        jvm()
        commonMain.dependencies {
            api(projects.libraries.architecture)
        }

        remove(commonTest.get())
    }
}
