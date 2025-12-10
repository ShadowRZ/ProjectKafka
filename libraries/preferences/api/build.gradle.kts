plugins {
    id("io.github.shadowrz.projectkafka.multiplatform")
}

kotlin {
    jvm()
    sourceSets {
        commonMain.dependencies {
            api(libs.androidx.datastore.preferences)
            api(libs.kotlinx.coroutines.core)
            api(projects.libraries.data.api)
        }

        remove(commonTest.get())
        remove(jvmTest.get())
    }
}
