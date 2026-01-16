plugins {
    alias(libs.plugins.projectkafka.feature)
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

dependencies {
    add("kspAndroid", libs.hanekokoro.framework.codegen)
    add("kspJvm", libs.hanekokoro.framework.codegen)
}
