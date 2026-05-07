plugins {
    alias(libs.plugins.projectkafka.multiplatform)
}

kotlin {
    jvm()
    sourceSets {
        commonMain.dependencies {
            api(libs.kotlinx.coroutines.core)
            api(project(":libraries:data:api"))
        }

        remove(commonTest.get())
        remove(jvmTest.get())
    }
}
