plugins {
    alias(libs.plugins.projectkafka.multiplatform)
    alias(libs.plugins.projectkafka.kotest)
}

kotlin {
    jvm()
    sourceSets {
        commonMain.dependencies {
            api(libs.okio)
            api(project(":libraries:uniqueid"))
        }

        commonTest.dependencies {
            implementation(libs.okio.fakefilesystem)
        }

        jvmTest.dependencies {
            implementation(libs.kotest.runner.junit6)
        }
    }
}
