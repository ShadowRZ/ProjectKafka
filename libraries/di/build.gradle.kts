plugins {
    alias(libs.plugins.projectkafka.multiplatform)
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
