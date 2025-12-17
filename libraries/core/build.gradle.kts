plugins {
    id("io.github.shadowrz.projectkafka.multiplatform")
}

kotlin {
    jvm()
    sourceSets {
        commonMain.dependencies {
            api(libs.androidx.compose.runtime.saveable)
            compileOnly(libs.androidx.compose.annotation)
            implementation(project.dependencies.platform(libs.androidx.compose.bom))
            implementation(libs.hanekokoro.framework.markers)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.uri)
        }

        remove(commonTest.get())
        remove(jvmTest.get())
    }
}
