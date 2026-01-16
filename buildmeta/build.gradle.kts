plugins {
    alias(libs.plugins.projectkafka.multiplatform)
}

kotlin {
    jvm()

    sourceSets {

        commonMain.dependencies {
            compileOnly(libs.compose.runtime)
        }

        remove(commonTest.get())
    }
}
