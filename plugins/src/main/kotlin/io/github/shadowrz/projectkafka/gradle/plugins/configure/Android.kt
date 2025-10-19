package io.github.shadowrz.projectkafka.gradle.plugins.configure

import Versions
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryTarget
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

internal fun CommonExtension<*, *, *, *, *, *>.configureAndroid() {
    defaultConfig {
        compileSdk = Versions.COMPILE_SDK
        minSdk = Versions.MIN_SDK

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        vectorDrawables {
            useSupportLibrary = true
            @Suppress("UnstableApiUsage")
            generatedDensities()
        }
    }

    compileOptions {
        sourceCompatibility = Versions.javaVersion
        targetCompatibility = Versions.javaVersion
        isCoreLibraryDesugaringEnabled = true
    }

    testOptions {
        unitTests.isReturnDefaultValues = true
    }
}

internal fun KotlinMultiplatformAndroidLibraryTarget.configureAndroid() {
    compileSdk = Versions.COMPILE_SDK
    minSdk = Versions.MIN_SDK

    enableCoreLibraryDesugaring = true

    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_21)
    }
}
