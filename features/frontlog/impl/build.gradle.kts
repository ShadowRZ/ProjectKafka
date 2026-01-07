plugins {
    id("io.github.shadowrz.projectkafka.feature")
}

kotlin {
    jvm()
    android {
        namespace = "io.github.shadowrz.projectkafka.features.frontlog.impl"
    }

    sourceSets {
        commonMain.dependencies {
            api(projects.features.frontlog.api)
            implementation(projects.libraries.components)
            implementation(projects.libraries.core)
            implementation(projects.libraries.data.api)
            implementation(projects.libraries.strings)
        }
    }
}
