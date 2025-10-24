plugins {
    id("io.github.shadowrz.projectkafka.multiplatform-module")
}

kotlin {
    jvm()
    sourceSets {
        commonMain.dependencies {
            api(libs.okio)
        }

        commonTest.dependencies {
            implementation(libs.kotest.assertions)
            implementation(libs.kotlin.test)
            implementation(libs.okio.fakefilesystem)
        }
    }
}
