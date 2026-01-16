package io.github.shadowrz.projectkafka.gradle.plugins.internal

import Versions
import io.github.shadowrz.projectkafka.gradle.plugins.dsl.android
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply

internal class AndroidPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            // No need for AGP 9
            // apply(plugin = "org.jetbrains.kotlin.android")
            apply<KotlinPlugin>()

            android {
                defaultConfig.apply {
                    compileSdk = Versions.COMPILE_SDK
                    minSdk = Versions.MIN_SDK

                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

                    vectorDrawables {
                        useSupportLibrary = true
                        @Suppress("UnstableApiUsage")
                        generatedDensities()
                    }
                }

                compileOptions.apply {
                    sourceCompatibility = JavaVersion.VERSION_21
                    targetCompatibility = JavaVersion.VERSION_21
                    isCoreLibraryDesugaringEnabled = true
                }
            }
        }
    }
}
