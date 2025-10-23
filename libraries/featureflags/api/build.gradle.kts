plugins {
    id("io.github.shadowrz.projectkafka.multiplatform-module")
}

kotlin {
    jvm()

    sourceSets {
        remove(commonTest.get())
    }
}
