import io.github.shadowrz.projectkafka.gradle.plugins.BuildMeta
import io.github.shadowrz.projectkafka.gradle.plugins.extensions.allFeaturesImpl
import io.github.shadowrz.projectkafka.gradle.plugins.extensions.allLibrariesImpl
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.compose.reload.gradle.AbstractComposeHotRun

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
    runtimeOnly(compose.desktop.currentOs)
    runtimeOnly(libs.kotlinx.coroutines.swing)
    implementation(libs.aboutlibraries.core)
    implementation(libs.coil.compose)
    implementation(libs.decompose)
    implementation(libs.decompose.compose)
    implementation(libs.directories)
    implementation(libs.filekit)
    implementation(libs.hanekokoro.framework.integration)
    implementation(libs.kermit)
    implementation(libs.okio)
    implementation(libs.slf4j)
    implementation(project(":buildmeta"))
    implementation(project(":compose-app"))
    implementation(project(":libraries:di"))
    implementation(project(":libraries:systemgraph"))
    // Add all implmentations
    implementation(project(":features:about:impl"))
    implementation(project(":features:createsystem:impl"))
    implementation(project(":features:datamanage:impl"))
    implementation(project(":features:editmember:impl"))
    implementation(project(":features:fronterindicator:impl"))
    implementation(project(":features:frontlog:impl"))
    implementation(project(":features:ftue:impl"))
    implementation(project(":features:home:impl"))
    implementation(project(":features:licenses:impl"))
    implementation(project(":features:preferences:impl"))
    implementation(project(":features:profile:impl"))
    implementation(project(":features:quickstart:impl"))
    implementation(project(":features:share:impl"))
    implementation(project(":features:switchsystem:impl"))
    implementation(project(":features:welcome:impl"))
    implementation(project(":libraries:cropper:impl"))
    implementation(project(":libraries:data:impl"))
    implementation(project(":libraries:featureflags:impl"))
    implementation(project(":libraries:permissions:impl"))
    implementation(project(":libraries:preferences:impl"))
}

compose.desktop {
    application {
        mainClass = "io.github.shadowrz.projectkafka.MainKt"

        nativeDistributions {
            modules(
                "java.sql",
                "jdk.security.auth",
                "jdk.unsupported",
            )

            targetFormats(
                TargetFormat.Dmg,
                TargetFormat.AppImage,
                TargetFormat.Exe,
            )
            packageName = BuildMeta.APPLICATION_ID
            packageVersion = BuildMeta.VERSION_NAME
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
            obfuscate = false
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
    buildConfigField("VERSION_NAME", BuildMeta.VERSION_NAME)
    buildConfigField("VERSION_CODE", BuildMeta.VERSION_CODE)
}

tasks.withType<Jar>().configureEach {
    archiveBaseName = "io.github.shadowrz.projectkafka"
}

// Use JetBrains Runtime 25 for Compose Hot Reload
tasks.withType<AbstractComposeHotRun>().configureEach {
    javaLauncher =
        javaToolchains
            .launcherFor {
                @Suppress("UnstableApiUsage")
                vendor = JvmVendorSpec.JETBRAINS
                languageVersion = JavaLanguageVersion.of(providers.gradleProperty("compose.reload.jbr.version").get())
            }
}
