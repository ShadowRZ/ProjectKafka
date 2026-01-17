import io.github.shadowrz.projectkafka.gradle.plugins.BuildMeta
import io.github.shadowrz.projectkafka.gradle.plugins.extensions.allFeaturesImpl
import io.github.shadowrz.projectkafka.gradle.plugins.extensions.allLibrariesImpl
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.projectkafka.library.jvm)
    alias(libs.plugins.projectkafka.compose)

    alias(libs.plugins.ksp)
    alias(libs.plugins.metro)
    alias(libs.plugins.aboutlibraries)
    alias(libs.plugins.buildconfig)
    alias(libs.plugins.compose.hotreload)
}

dependencies {
    implementation(compose.desktop.currentOs)
    implementation(libs.aboutlibraries.core)
    implementation(libs.circuit.sharedelements)
    implementation(libs.coil.compose)
    implementation(libs.decompose)
    implementation(libs.decompose.compose)
    implementation(libs.directories)
    implementation(libs.hanekokoro.framework.annotations)
    implementation(libs.hanekokoro.framework.integration)
    implementation(libs.hanekokoro.framework.runtime)
    implementation(libs.kotlinx.coroutines.swing)
    implementation(libs.okio)
    implementation(projects.appDependencies)
    implementation(projects.appNavigation)
    implementation(projects.buildmeta)
    implementation(projects.libraries.di)
    implementation(projects.libraries.systemgraph)
    // Add all implmentations
    allFeaturesImpl(project)
    allLibrariesImpl(project)
    // KSP
    ksp(libs.hanekokoro.framework.codegen)
}

compose.desktop {
    application {
        mainClass = "io.github.shadowrz.projectkafka.MainKt"

        nativeDistributions {
            modules("java.sql", "jdk.unsupported")

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
            version = libs.versions.proguard.get()
            configurationFiles.from(project.file("proguard-rules.pro"))
            obfuscate = true
            optimize = true
            joinOutputJars = true
        }
    }
}

aboutLibraries {
    export {
        outputFile = file("src/main/resources/aboutlibraries.json")
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

buildConfig {
    packageName = "io.github.shadowrz.projectkafka"
    className = "BuildConfig"

    buildConfigField("APPLICATION_ID", BuildMeta.APPLICATION_ID)
    buildConfigField("APPLICATION_NAME", BuildMeta.APPLICATION_NAME)
    buildConfigField("VERSION_NAME", Versions.VERSION_NAME)
    buildConfigField("VERSION_CODE", Versions.VERSION_CODE)
}

tasks.withType<Jar>().configureEach {
    archiveBaseName = "io.github.shadowrz.projectkafka"
}
