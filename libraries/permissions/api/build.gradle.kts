plugins {
    id("io.github.shadowrz.projectkafka.multiplatform-module")
    id("io.github.shadowrz.projectkafka.compose-module")
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
