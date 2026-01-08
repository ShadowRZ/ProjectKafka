plugins {
    id("io.github.shadowrz.projectkafka.multiplatform")
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    jvm()
    sourceSets {
        commonMain.dependencies {
            api(libs.androidx.paging.common)
            api(libs.kotlinx.datetime)
            api(libs.uri)
            compileOnly(libs.compose.runtime)
            implementation(libs.kotlinx.serialization.core)
        }

        remove(commonTest.get())
    }
}
