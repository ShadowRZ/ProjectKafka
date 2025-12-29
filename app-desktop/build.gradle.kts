import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    id("io.github.shadowrz.projectkafka.multiplatform")
    id("io.github.shadowrz.projectkafka.compose")
    alias(libs.plugins.ksp)
    alias(libs.plugins.metro)
    alias(libs.plugins.aboutlibraries)
}

kotlin {
    jvm()

    sourceSets {
        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
            implementation(projects.buildmeta)
        }
    }
}

compose.desktop {
    application {
        mainClass = "io.github.shadowrz.projectkafka.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.AppImage)
            packageName = "io.github.shadowrz.projectkafka"
            packageVersion = "1.0"
        }
    }
}
