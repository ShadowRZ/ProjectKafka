@file:Suppress("UnstableApiUsage")

import com.android.build.gradle.tasks.GenerateBuildConfig
import io.github.shadowrz.projectkafka.gradle.plugins.BuildMeta
import io.github.shadowrz.projectkafka.gradle.plugins.extensions.allFeaturesImpl
import io.github.shadowrz.projectkafka.gradle.plugins.extensions.allLibrariesImpl

plugins {
    id("io.github.shadowrz.projectkafka.application")
    id("io.github.shadowrz.projectkafka.compose")
    alias(libs.plugins.ksp)
    alias(libs.plugins.metro)
    alias(libs.plugins.aboutlibraries)
}

android {
    namespace = "io.github.shadowrz.projectkafka"

    defaultConfig {
        applicationId = BuildMeta.APPLICATION_ID
        versionCode = Versions.VERSION_CODE
        versionName = Versions.VERSION_NAME

        targetSdk = Versions.TARGET_SDK
    }

    buildFeatures {
        buildConfig = true
    }

    androidResources {
        generateLocaleConfig = true
        localeFilters += setOf("zh-rCN")
    }

    signingConfigs {
        getByName("debug") {
            keyAlias = "androiddebugkey"
            keyPassword = "android"
            storeFile =
                isolated.rootProject.projectDirectory
                    .file("signatures/debug.keystore")
                    .asFile
            storePassword = "android"
        }
    }

    buildTypes {
        debug {
            applicationIdSuffix = BuildMeta.Variants.DEBUG.applicationIdSuffix
            versionNameSuffix = BuildMeta.Variants.DEBUG.versionNameSuffix
            resValue("string", "app_name_flavored", BuildMeta.Variants.DEBUG.applicationName())

            isPseudoLocalesEnabled = true
        }
        release {
            resValue("string", "app_name_flavored", BuildMeta.Variants.RELEASE.applicationName())

            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
}

aboutLibraries {
    export {
        outputFile = file("src/main/res/raw/aboutlibraries.json")
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

dependencies {
    // Assets
    implementation(projects.assets)
    // Project dependencies
    implementation(projects.appnav)
    implementation(projects.buildmeta)
    implementation(projects.libraries.androidutils)
    implementation(projects.libraries.architecture)
    implementation(projects.libraries.core)
    implementation(projects.libraries.di)
    implementation(projects.libraries.strings)
    implementation(libs.aboutlibraries.core)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.browser)
    implementation(libs.androidx.sharetarget)
    implementation(libs.androidx.splashscreen)
    implementation(libs.androidx.startup)
    implementation(libs.androidx.preference)
    implementation(libs.circuit.sharedelements)
    implementation(libs.coil.compose)
    implementation(libs.decompose)
    implementation(libs.hanekokoro.framework.annotations)
    implementation(libs.hanekokoro.framework.integration)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.okio)
    ksp(libs.hanekokoro.framework.codegen)
    // Add all implmentations
    allFeaturesImpl(project)
    allLibrariesImpl(project)
}

tasks.withType<GenerateBuildConfig>().configureEach {
    outputs.upToDateWhen { false }
    android.defaultConfig.buildConfigField(
        type = "String",
        name = "APPLICATION_NAME",
        value = "\"${BuildMeta.APPLICATION_NAME}\"",
    )
}
