plugins {
    alias(libs.plugins.projectkafka.feature)
    alias(libs.plugins.projectkafka.kotest)
}

kotlin {
    jvm()
    android {
        namespace = "io.github.shadowrz.projectkafka.features.licenses.impl"
    }

    sourceSets {
        commonMain.dependencies {
            api(project(":features:licenses:api"))
            implementation(libs.aboutlibraries.core)
            implementation(libs.aboutlibraries.compose.material3)
            implementation(project(":designsystem"))
            implementation(project(":libraries:architecture"))
            implementation(project(":libraries:strings"))
        }

        commonTest.dependencies {
            implementation(libs.kotlinx.coroutines.test)
            implementation(libs.molecule.runtime)
            implementation(libs.turbine)
            implementation(project(":tests:utils"))
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
