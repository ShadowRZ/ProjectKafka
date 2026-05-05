package io.github.shadowrz.projectkafka.gradle.plugins.internal

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryTarget
import io.github.shadowrz.projectkafka.gradle.plugins.BuildMeta
import io.github.shadowrz.projectkafka.gradle.plugins.ConfigurationNames
import io.github.shadowrz.projectkafka.gradle.plugins.PluginIds
import libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

@Suppress("UnstableApiUsage")
internal class AndroidPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            // No need for AGP 9
            // apply(plugin = "org.jetbrains.kotlin.android")
            apply<KotlinPlugin>()

            pluginManager.withPlugin(PluginIds.AGP_BASE) {
                extensions.configure(CommonExtension::class.java) {
                    defaultConfig.apply {
                        compileSdk = BuildMeta.COMPILE_SDK
                        minSdk = BuildMeta.MIN_SDK
                        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

                        vectorDrawables {
                            useSupportLibrary = true
                            generatedDensities()
                        }
                    }

                    compileOptions.apply {
                        sourceCompatibility = BuildMeta.javaVersion
                        targetCompatibility = BuildMeta.javaVersion
                        isCoreLibraryDesugaringEnabled = true
                    }

                    testOptions.unitTests.all { it.useJUnitPlatform() }
                }

                dependencies.add(ConfigurationNames.CORE_LIBRARY_DESUGARING, libs.desugar)
            }

            pluginManager.withPlugin(PluginIds.AGP_APPLICATION) {
                extensions.configure(ApplicationExtension::class.java) {
                    signingConfigs.configureEach {
                        enableV3Signing = true
                        enableV4Signing = true
                    }
                }
            }

            pluginManager.withPlugin(PluginIds.KOTLIN_MULTIPLATFORM) {
                pluginManager.withPlugin(PluginIds.AGP_LIBRARY_MULTIPLATFORM) {
                    val kotlin = extensions.getByType(KotlinMultiplatformExtension::class.java)

                    kotlin.targets.withType(KotlinMultiplatformAndroidLibraryTarget::class.java).configureEach {
                        compileSdk = BuildMeta.COMPILE_SDK
                        minSdk = BuildMeta.MIN_SDK

                        enableCoreLibraryDesugaring = true
                    }

                    dependencies.add(ConfigurationNames.CORE_LIBRARY_DESUGARING, libs.desugar)
                }
            }
        }
    }
}
