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
            api(project(":features:share:api"))
            implementation(project(":designsystem"))
            implementation(project(":libraries:di"))
            implementation(project(":libraries:strings"))
        }
    }
}

dependencies {
    add("kspAndroid", libs.hanekokoro.framework.codegen)
    add("kspJvm", libs.hanekokoro.framework.codegen)
}
