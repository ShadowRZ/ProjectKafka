package io.github.shadowrz.projectkafka.gradle.plugins.internal

import io.github.shadowrz.projectkafka.gradle.plugins.BuildMeta
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.HasConfigurableKotlinCompilerOptions
import org.jetbrains.kotlin.gradle.dsl.KotlinBaseExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompilerOptions
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.targets.jvm.KotlinJvmTarget

/** Configures all foundational Kotlin configurations. */
internal class KotlinPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.extensions.configure(KotlinBaseExtension::class.java) {
            if (this is HasConfigurableKotlinCompilerOptions<*>) {
                compilerOptions {
                    progressiveMode.set(true)
                    allWarningsAsErrors.set(true)
                    freeCompilerArgs.addAll(
                        "-Xannotation-default-target=param-property",
                        "-Xconsistent-data-class-copy-visibility",
                        "-Xexpect-actual-classes",
                        // Should not need this after Kotlin 2.4.0
                        "-Xcontext-parameters",
                    )

                    if (this is KotlinJvmCompilerOptions) {
                        jvmTarget.convention(BuildMeta.jvmTarget)
                    }
                }
            }

            if (this is KotlinMultiplatformExtension) {
                // Use default hierarchy template
                applyDefaultHierarchyTemplate()
                // In KMP projects, compiler options are per-target
                targets.configureEach {
                    if (this is HasConfigurableKotlinCompilerOptions<*>) {
                        compilerOptions {
                            if (this is KotlinJvmCompilerOptions) {
                                jvmTarget.convention(BuildMeta.jvmTarget)
                            }
                        }
                    }

                    // See below, only for JVM targets in KMP.
                    if (this is KotlinJvmTarget) {
                        compilerOptions { freeCompilerArgs.add("-Xjdk-release=${BuildMeta.JAVA_VERSION}") }
                    }
                }
            }

            // https://jakewharton.com/kotlins-jdk-release-compatibility-flag/
            if (this is KotlinJvmExtension) {
                compilerOptions { freeCompilerArgs.add("-Xjdk-release=${BuildMeta.JAVA_VERSION}") }
            }
        }
    }
}
