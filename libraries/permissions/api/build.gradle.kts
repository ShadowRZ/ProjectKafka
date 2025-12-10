plugins {
    id("io.github.shadowrz.projectkafka.multiplatform")
    id("io.github.shadowrz.projectkafka.compose")
}

kotlin {
    jvm()
    sourceSets {
        commonMain.dependencies {
            implementation(libs.androidx.annotation)
        }

        remove(commonTest.get())
        remove(jvmTest.get())
    }
}
