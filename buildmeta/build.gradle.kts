plugins {
    id("io.github.shadowrz.projectkafka.multiplatform")
}

kotlin {
    sourceSets {
        jvm()
        commonMain.dependencies {
            compileOnly(libs.compose.runtime)
        }

        remove(commonTest.get())
    }
}
