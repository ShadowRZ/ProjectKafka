package io.github.shadowrz.projectkafka.gradle.plugins.internal

import io.github.shadowrz.projectkafka.gradle.plugins.BuildMeta
import io.github.shadowrz.projectkafka.gradle.plugins.dsl.android
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
                    compileSdk = BuildMeta.COMPILE_SDK
                    minSdk = BuildMeta.MIN_SDK

                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

                    vectorDrawables {
                        useSupportLibrary = true
                        @Suppress("UnstableApiUsage")
                        generatedDensities()
                    }
                }

                compileOptions.apply {
                    sourceCompatibility = BuildMeta.javaVersion
                    targetCompatibility = BuildMeta.javaVersion
                    isCoreLibraryDesugaringEnabled = true
                }
            }
        }
    }
}
