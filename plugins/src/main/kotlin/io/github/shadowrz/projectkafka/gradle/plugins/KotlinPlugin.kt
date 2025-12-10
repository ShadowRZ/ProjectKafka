package io.github.shadowrz.projectkafka.gradle.plugins

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/**
 * Configures all foundational Kotlin configurations.
 */
class KotlinPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            tasks.withType<KotlinCompile> {
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_21)
                    allWarningsAsErrors.set(true)

                    freeCompilerArgs.addAll(
                        "-Xannotation-default-target=param-property",
                        "-Xconsistent-data-class-copy-visibility",
                    )
                }
            }
        }
    }
}
