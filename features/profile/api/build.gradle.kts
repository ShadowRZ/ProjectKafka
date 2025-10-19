plugins {
    alias(libs.plugins.kotlin.multiplatform)
}

kotlin {
    sourceSets {
        jvm()
        commonMain.dependencies {
            api(projects.libraries.architecture)
            api(projects.libraries.data.api)
        }

        remove(commonTest.get())
    }
}
