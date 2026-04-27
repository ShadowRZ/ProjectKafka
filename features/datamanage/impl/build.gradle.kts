plugins {
    alias(libs.plugins.projectkafka.feature)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    jvm()
    android {
        namespace = "io.github.shadowrz.projectkafka.features.datamanage.impl"

        androidResources {
            enable = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            api(project(":features:datamanage:api"))
            implementation(libs.kermit)
            implementation(libs.okio)
            implementation(libs.sqldelight.runtime)
            implementation(project(":designsystem"))
            implementation(project(":libraries:architecture"))
            implementation(project(":libraries:core"))
            implementation(project(":libraries:data:api"))
            implementation(project(":libraries:di"))
            implementation(project(":libraries:strings"))
            implementation(project(":libraries:systemgraph"))
            implementation(project(":libraries:zipwriter"))
        }
        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
        }

        remove(commonTest.get())
    }
}

dependencies {
    add("kspAndroid", libs.hanekokoro.framework.codegen)
    add("kspJvm", libs.hanekokoro.framework.codegen)
}
