import io.github.shadowrz.projectkafka.gradle.plugins.BuildMeta
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
    alias(libs.plugins.compose)
}

dependencyAnalysis {
    app()
}

dependencies {
    runtimeOnly(compose.desktop.currentOs)
    runtimeOnly(libs.kotlinx.coroutines.swing)
    implementation(libs.aboutlibraries.core)
    implementation(libs.coil.compose)
    implementation(libs.directories)
    implementation(libs.filekit)
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
    implementation(project(":features:messages:impl"))
    implementation(project(":features:preferences:impl"))
    implementation(project(":features:profile:impl"))
    implementation(project(":features:quickstart:impl"))
    implementation(project(":features:share:impl"))
    implementation(project(":features:switchsystem:impl"))
    implementation(project(":features:welcome:impl"))
    implementation(project(":libraries:cropper:impl"))
    implementation(project(":libraries:data:impl"))
    implementation(project(":libraries:featureflags:impl"))
    implementation(project(":libraries:kafkastate:impl"))
    implementation(project(":libraries:permissions:impl"))
    implementation(project(":libraries:preferences:impl"))
}

val versionCodeP = providers.gradleProperty("projectkafka.version-code").map { it.toInt() }
val versionNameP = providers.gradleProperty("projectkafka.version-name")

compose.desktop {
    application {
        mainClass = "io.github.shadowrz.projectkafka.MainKt"

        nativeDistributions {
            modules(
                "java.sql",
                "jdk.security.auth",
                "jdk.unsupported",
            )
            jvmArgs("--enable-native-access=ALL-UNNAMED")
            javaHome =
                javaToolchains
                    .launcherFor {
                        @Suppress("UnstableApiUsage")
                        vendor = JvmVendorSpec.JETBRAINS
                        @Suppress("UnstableApiUsage")
                        languageVersion = JavaLanguageVersion.current()
                    }
                    .get()
                    .metadata
                    .installationPath
                    .asFile
                    .absolutePath
            targetFormats(
                TargetFormat.Dmg,
                TargetFormat.AppImage,
                TargetFormat.Exe,
            )
            packageName = BuildMeta.APPLICATION_ID
            description = BuildMeta.APPLICATION_NAME
            packageVersion = versionNameP.get()
            copyright = "© 2025-2026 @ShadowRZ"
            vendor = "@ShadowRZ"
            @Suppress("UnstableApiUsage")
            licenseFile = project.isolated.rootProject.projectDirectory.file("COPYING.md")

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
    buildConfigField("VERSION_NAME", versionNameP.get())
    buildConfigField("VERSION_CODE", versionCodeP.get())
}

tasks.withType<Jar>().configureEach {
    archiveBaseName = "io.github.shadowrz.projectkafka"
}

// Use JetBrains Runtime 25 for Compose Hot Reload
tasks.withType<AbstractComposeHotRun>().configureEach {
    javaLauncher = javaToolchains.launcherFor {
        @Suppress("UnstableApiUsage")
        vendor = JvmVendorSpec.JETBRAINS
        languageVersion = JavaLanguageVersion.of(providers.gradleProperty("compose.reload.jbr.version").get())
    }
}

tasks.withType<JavaExec>().configureEach {
    jvmArgs("--enable-native-access=ALL-UNNAMED")
}
