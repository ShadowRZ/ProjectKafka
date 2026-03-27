plugins {
    alias(libs.plugins.projectkafka.multiplatform)
    alias(libs.plugins.projectkafka.kotest)
}

kotlin {
    jvm()
    sourceSets {
        commonMain.dependencies {
            api(libs.okio)
        }

        remove(commonTest.get())
    }
}
