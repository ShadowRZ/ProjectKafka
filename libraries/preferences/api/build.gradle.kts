plugins {
    id("io.github.shadowrz.projectkafka.multiplatform-module")
}

kotlin {
    jvm()
    sourceSets {
        commonMain.dependencies {
            api(projects.libraries.data.api)
            api(libs.kotlinx.coroutines.core)
        }

        remove(commonTest.get())
        remove(jvmTest.get())
    }
}
