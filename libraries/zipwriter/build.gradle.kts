plugins {
    id("io.github.shadowrz.projectkafka.multiplatform-module")
    id("io.github.shadowrz.projectkafka.kotest-module")
}

kotlin {
    jvm()
    sourceSets {
        commonMain.dependencies {
            api(libs.okio)
        }

        commonTest.dependencies {
            implementation(libs.kotest.assertions)
            implementation(libs.kotest.runner.junit5)
            implementation(libs.okio.fakefilesystem)
        }
    }
}
