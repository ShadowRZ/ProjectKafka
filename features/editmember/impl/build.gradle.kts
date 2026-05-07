plugins {
    alias(libs.plugins.projectkafka.feature)
    alias(libs.plugins.projectkafka.kotest)
}

kotlin {
    jvm()
    android {
        namespace = "io.github.shadowrz.projectkafka.features.editmember.impl"
    }

    sourceSets {
        commonMain.dependencies {
            api(project(":features:editmember:api"))
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.androidx.navigationevent.compose)
            implementation(libs.kotlinx.datetime)
            implementation(libs.krop.core)
            implementation(libs.krop.ui)
            implementation(project(":buildmeta"))
            implementation(project(":designsystem"))
            implementation(project(":libraries:architecture"))
            implementation(project(":libraries:core"))
            implementation(project(":libraries:cropper:api"))
            implementation(project(":libraries:data:api"))
            implementation(project(":libraries:di"))
            implementation(project(":libraries:kafkaui"))
            implementation(project(":libraries:strings"))
        }

        commonTest.dependencies {
            implementation(libs.kotest.assertions)
            implementation(libs.kotlinx.coroutines.test)
            implementation(libs.molecule.runtime)
            implementation(libs.turbine)
            implementation(libs.uri)
            implementation(project(":libraries:data:test"))
            implementation(project(":libraries:cropper:test"))
            implementation(project(":tests:utils"))
        }

        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
        }

        jvmTest.dependencies {
            implementation(libs.kotest.runner.junit6)
        }
    }
}

dependencies {
    add("kspAndroid", libs.hanekokoro.framework.codegen)
    add("kspJvm", libs.hanekokoro.framework.codegen)
}
