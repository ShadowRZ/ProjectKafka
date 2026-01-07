plugins {
    id("io.github.shadowrz.projectkafka.feature")
}

kotlin {
    jvm()
    android {
        namespace = "io.github.shadowrz.projectkafka.features.share.impl"
    }

    sourceSets {
        commonMain.dependencies {
            api(projects.features.share.api)
            implementation(projects.libraries.di)
            implementation(projects.libraries.icons)
            implementation(projects.libraries.strings)
        }
    }
}
