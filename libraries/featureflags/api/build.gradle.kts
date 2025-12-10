plugins {
    id("io.github.shadowrz.projectkafka.multiplatform")
}

kotlin {
    jvm()

    sourceSets {
        remove(commonTest.get())
    }
}
