plugins {
    alias(libs.plugins.projectkafka.multiplatform)
    alias(libs.plugins.projectkafka.compose)
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
