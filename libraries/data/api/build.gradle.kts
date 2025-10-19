plugins {
    id("io.github.shadowrz.projectkafka.multiplatform-module")
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    jvm()
    sourceSets {
        commonMain.dependencies {
            api(libs.androidx.paging.common)
            api(libs.kotlinx.datetime)
            api(libs.uri)
            compileOnly(libs.androidx.compose.annotation)
            implementation(project.dependencies.platform(libs.androidx.compose.bom))
            implementation(libs.kotlinx.serialization.core)
        }

        remove(commonTest.get())
    }
}
