plugins {
    id("io.github.shadowrz.projectkafka.multiplatform")
}

kotlin {
    jvm()
    sourceSets {
        commonMain.dependencies {
            compileOnly(libs.compose.runtime)
            implementation(libs.coil.core)
            implementation(libs.hanekokoro.framework.markers)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.uri)
        }

        remove(commonTest.get())
    }
}
