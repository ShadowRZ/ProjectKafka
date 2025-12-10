plugins {
    id("io.github.shadowrz.projectkafka.feature")
}

kotlin {
    android {
        namespace = "io.github.shadowrz.projectkafka.features.fronterindicator.impl"
    }

    sourceSets {
        commonMain.dependencies {
            api(projects.features.fronterindicator.api)
            implementation(projects.libraries.core)
        }

        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
            implementation(projects.libraries.components)
            implementation(projects.libraries.icons)
            implementation(projects.libraries.strings)
        }
    }
}
