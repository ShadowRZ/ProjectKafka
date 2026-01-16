plugins {
    alias(libs.plugins.projectkafka.multiplatform)
}

kotlin {
    jvm()

    sourceSets {
        remove(commonTest.get())
    }
}
