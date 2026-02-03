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

        commonTest.dependencies {
            implementation(libs.kotest.assertions)
            implementation(libs.kotest.runner.junit6)
            implementation(libs.okio.fakefilesystem)
        }
    }
}
