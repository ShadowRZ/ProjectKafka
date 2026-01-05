import com.codingfeline.buildkonfig.compiler.FieldSpec
import io.github.shadowrz.projectkafka.gradle.plugins.BuildMeta
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    id("io.github.shadowrz.projectkafka.multiplatform")
    id("io.github.shadowrz.projectkafka.compose")
    alias(libs.plugins.ksp)
    alias(libs.plugins.metro)
    alias(libs.plugins.aboutlibraries)
    alias(libs.plugins.buildkonfig)
}

kotlin {
    jvm()

    sourceSets {
        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.aboutlibraries.core)
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
            packageName = BuildMeta.APPLICATION_ID
            packageVersion = Versions.VERSION_NAME
            description = BuildMeta.APPLICATION_NAME
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

aboutLibraries {
    export {
        outputFile = file("src/jvmMain/resources/aboutlibraries.json")
        variant = "jvm"
        excludeFields.addAll("description", "organization", "scm", "funding")
        prettyPrint = true
    }

    library {
        // Enable the duplication mode, allows to merge, or link dependencies which relate
        duplicationMode = com.mikepenz.aboutlibraries.plugin.DuplicateMode.MERGE
        // Configure the duplication rule, to match "duplicates" with
        duplicationRule = com.mikepenz.aboutlibraries.plugin.DuplicateRule.SIMPLE
    }
}

buildkonfig {
    packageName = "io.github.shadowrz.projectkafka"
    objectName = "BuildConfig"

    defaultConfigs {
        buildConfigField(
            type = FieldSpec.Type.STRING,
            name = "APPLICATION_ID",
            value = BuildMeta.APPLICATION_ID,
        )
        buildConfigField(
            type = FieldSpec.Type.STRING,
            name = "APPLICATION_NAME",
            value = BuildMeta.APPLICATION_NAME,
        )
        buildConfigField(
            type = FieldSpec.Type.STRING,
            name = "VERSION_NAME",
            value = Versions.VERSION_NAME,
        )
        buildConfigField(
            type = FieldSpec.Type.INT,
            name = "VERSION_CODE",
            value = "${Versions.VERSION_CODE}",
        )
    }
}
