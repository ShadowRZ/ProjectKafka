plugins {
    id("io.github.shadowrz.projectkafka.multiplatform")
}

kotlin {
    jvm()
    sourceSets {
        commonMain.dependencies {
            implementation(libs.metro.runtime)
        }

        remove(commonTest.get())
        remove(jvmTest.get())
    }
}
