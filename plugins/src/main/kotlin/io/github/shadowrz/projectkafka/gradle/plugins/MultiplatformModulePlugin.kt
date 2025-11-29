package io.github.shadowrz.projectkafka.gradle.plugins

import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryTarget
import io.github.shadowrz.projectkafka.gradle.plugins.configure.configureAndroid
import io.github.shadowrz.projectkafka.gradle.plugins.configure.configureKotlin
import io.github.shadowrz.projectkafka.gradle.plugins.extensions.coreLibraryDesugaring
import libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.invoke
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class MultiplatformModulePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "io.github.shadowrz.projectkafka.codestyle")
            apply(plugin = "org.jetbrains.kotlin.multiplatform")

            configureKotlin<KotlinMultiplatformExtension>()

            extensions.configure<KotlinMultiplatformExtension> {
                applyDefaultHierarchyTemplate()

                compilerOptions {
                    freeCompilerArgs.add("-Xexpect-actual-classes")
                }

                sourceSets {
                    findByName("commonMain")?.apply {
                        dependencies {
                            implementation(libs.uri)
                        }
                    }
                }
            }

            pluginManager.withPlugin("io.github.shadowrz.projectkafka.library") {
                apply(plugin = "com.android.kotlin.multiplatform.library")

                extensions.configure<KotlinMultiplatformExtension> {
                    sourceSets {
                        androidMain.dependencies {
                            implementation(libs.timber)
                        }
                    }
                    extensions.configure<KotlinMultiplatformAndroidLibraryTarget> {
                        configureAndroid()
                    }
                }

                dependencies {
                    coreLibraryDesugaring(libs.desugar)
                }
            }

            pluginManager.withPlugin("io.github.shadowrz.projectkafka.application") {
                extensions.configure<KotlinMultiplatformExtension> {
                    androidTarget()
                }
            }
        }
    }
}
