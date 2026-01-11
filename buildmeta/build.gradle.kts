plugins {
    id("io.github.shadowrz.projectkafka.multiplatform")
}

kotlin {
    jvm()

    sourceSets {

        commonMain.dependencies {
            compileOnly(libs.compose.runtime)
        }

        remove(commonTest.get())
    }
}
