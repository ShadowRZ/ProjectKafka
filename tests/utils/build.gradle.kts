plugins {
    alias(libs.plugins.projectkafka.multiplatform)
    alias(libs.plugins.projectkafka.compose)
}

kotlin {
    sourceSets {
        jvm()
        commonMain.dependencies {
            implementation(libs.kotest.framework.engine)
            implementation(libs.kotlinx.coroutines.test)
            implementation(libs.molecule.runtime)
            implementation(libs.turbine)
            implementation(projects.libraries.architecture)
        }

        remove(commonTest.get())
    }
}
