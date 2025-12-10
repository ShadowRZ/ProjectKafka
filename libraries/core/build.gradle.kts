plugins {
    id("io.github.shadowrz.projectkafka.multiplatform")
}

kotlin {
    jvm()
    sourceSets {
        commonMain.dependencies {
            implementation(project.dependencies.platform(libs.androidx.compose.bom))
            api(libs.androidx.compose.runtime.saveable)
            compileOnly(libs.androidx.compose.annotation)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.uri)
        }

        remove(commonTest.get())
        remove(jvmTest.get())
    }
}
