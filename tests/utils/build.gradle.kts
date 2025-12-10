plugins {
    id("io.github.shadowrz.projectkafka.multiplatform")
    id("io.github.shadowrz.projectkafka.compose")
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
