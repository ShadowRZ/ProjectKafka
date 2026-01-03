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
            implementation(libs.directories)
            implementation(libs.kotlinx.coroutines.swing)
            implementation(libs.okio)
            implementation(projects.buildmeta)
            implementation(projects.libraries.di)
        }
    }
}

compose.desktop {
    application {
        mainClass = "io.github.shadowrz.projectkafka.MainKt"

        nativeDistributions {
            targetFormats(
                TargetFormat.Dmg,
                TargetFormat.AppImage,
                TargetFormat.Exe,
            )
            packageName = "io.github.shadowrz.projectkafka"
            packageVersion = "1.0"
            description = "Project Kafka"
            copyright = "© 2025-2026 @ShadowRZ"
            vendor = "@ShadowRZ"
            @Suppress("UnstableApiUsage")
            licenseFile =
                project.isolated.rootProject.projectDirectory
                    .file("COPYING.md")

            macOS {
                iconFile.set(project.file("assets/io.github.shadowrz.projectkafka.icns"))
            }
            windows {
                iconFile.set(project.file("assets/io.github.shadowrz.projectkafka.ico"))
                packageVersion = "1.0.0"
            }
            linux {
                iconFile.set(project.file("assets/io.github.shadowrz.projectkafka.png"))
            }
        }

        buildTypes.release.proguard {
            configurationFiles.from(project.file("proguard-rules.pro"))
            obfuscate = true
            optimize = true
            joinOutputJars = true
        }
    }
}
