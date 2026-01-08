plugins {
    id("io.github.shadowrz.projectkafka.feature")
}

kotlin {
    jvm()
    android {
        namespace = "io.github.shadowrz.projectkafka.features.fronterindicator.impl"

        androidResources {
            enable = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            api(projects.features.fronterindicator.api)
            implementation(projects.libraries.components)
            implementation(projects.libraries.core)
            implementation(projects.libraries.icons)
            implementation(projects.libraries.strings)
        }

        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
        }
    }
}

dependencies {
    add("kspAndroid", libs.hanekokoro.framework.codegen)
    add("kspJvm", libs.hanekokoro.framework.codegen)
}
