package io.github.shadowrz.projectkafka.gradle.plugins

import com.android.build.api.dsl.LibraryExtension
import io.github.shadowrz.projectkafka.gradle.plugins.extensions.testImplementation
import libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KotestPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "io.kotest")
            apply(plugin = "com.google.devtools.ksp")
            apply(plugin = "org.jetbrains.kotlinx.kover")

            // Ensure we use JUnit Platform
            tasks.withType<Test>().configureEach {
                useJUnitPlatform()
                filter {
                    isFailOnNoMatchingTests = false
                }
            }

            pluginManager.withPlugin("com.android.library") {
                extensions.configure<LibraryExtension> {
                    testOptions {
                        unitTests.all {
                            it.useJUnitPlatform()
                        }
                    }
                }

                dependencies {
                    testImplementation(libs.kotest.runner.junit5)
                    testImplementation(libs.kotest.assertions)
                }
            }
            pluginManager.withPlugin("com.android.application") {
                extensions.configure<LibraryExtension> {
                    testOptions {
                        unitTests.all {
                            it.useJUnitPlatform()
                        }
                    }
                }

                dependencies {
                    testImplementation(libs.kotest.runner.junit5)
                    testImplementation(libs.kotest.assertions)
                }
            }

            // KMP
            pluginManager.withPlugin("org.jetbrains.kotlin.multiplatform") {
                extensions.configure<KotlinMultiplatformExtension> {
                    sourceSets {
                        commonTest.dependencies {
                            implementation(libs.kotest.framework.engine)
                            implementation(libs.kotest.assertions)
                        }
                    }
                }
            }
        }
    }
}
