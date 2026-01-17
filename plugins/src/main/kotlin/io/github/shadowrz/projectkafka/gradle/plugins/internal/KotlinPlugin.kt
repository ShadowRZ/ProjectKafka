package io.github.shadowrz.projectkafka.gradle.plugins.internal

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/**
 * Configures all foundational Kotlin configurations.
 */
internal class KotlinPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            tasks.withType<KotlinCompile>().configureEach {
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_25)
                    allWarningsAsErrors.set(true)

                    freeCompilerArgs.addAll(
                        "-Xannotation-default-target=param-property",
                        "-Xconsistent-data-class-copy-visibility",
                        "-Xcontext-parameters",
                    )
                }
            }

            pluginManager.withPlugin("org.jetbrains.kotlin.multiplatform") {
                extensions.configure<KotlinMultiplatformExtension> {
                    compilerOptions {
                        allWarningsAsErrors.set(true)

                        freeCompilerArgs.addAll(
                            "-Xannotation-default-target=param-property",
                            "-Xconsistent-data-class-copy-visibility",
                            "-Xexpect-actual-classes",
                            "-Xcontext-parameters",
                        )
                    }
                }
            }
        }
    }
}
