plugins {
    id("io.github.shadowrz.projectkafka.multiplatform-module")
    id("io.github.shadowrz.projectkafka.compose-module")
    id("io.github.shadowrz.projectkafka.library")
}

kotlin {
    jvm()
    android {
        namespace = "io.github.shadowrz.projectkafka.libraries.crop"
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.krop.core)
            implementation(projects.libraries.icons)
        }

        remove(commonTest.get())
    }
}
