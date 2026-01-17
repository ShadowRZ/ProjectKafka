package io.github.shadowrz.projectkafka.gradle.plugins

import Versions
import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryTarget
import io.github.shadowrz.projectkafka.gradle.plugins.extensions.coreLibraryDesugaring
import io.github.shadowrz.projectkafka.gradle.plugins.internal.FoundationPlugin
import libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.invoke
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class MultiplatformPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "org.jetbrains.kotlin.multiplatform")
            apply<FoundationPlugin>()

            extensions.configure<KotlinMultiplatformExtension> {
                applyDefaultHierarchyTemplate()

                sourceSets {
                    commonMain.dependencies {
                        implementation(libs.uri)
                    }
                }
            }

            pluginManager.withPlugin("com.android.kotlin.multiplatform.library") {
                extensions.configure<KotlinMultiplatformExtension> {
                    sourceSets {
                        androidMain.dependencies {
                            implementation(libs.timber)
                        }
                    }
                    extensions.configure<KotlinMultiplatformAndroidLibraryTarget> {
                        compileSdk = Versions.COMPILE_SDK
                        minSdk = Versions.MIN_SDK

                        enableCoreLibraryDesugaring = true

                        compilerOptions {
                            jvmTarget.set(Versions.jvmTarget)
                        }
                    }
                }

                dependencies {
                    coreLibraryDesugaring(libs.desugar)
                }
            }
        }
    }
}
