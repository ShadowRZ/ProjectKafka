plugins {
    id("io.github.shadowrz.projectkafka.multiplatform")
}

kotlin {
    sourceSets {
        jvm()
        commonMain.dependencies {
            compileOnly(project.dependencies.platform(libs.androidx.compose.bom))
            compileOnly(libs.androidx.compose.annotation)
        }

        remove(commonTest.get())
    }
}
