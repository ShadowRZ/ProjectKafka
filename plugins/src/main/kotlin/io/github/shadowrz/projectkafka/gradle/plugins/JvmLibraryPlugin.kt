package io.github.shadowrz.projectkafka.gradle.plugins

import io.github.shadowrz.projectkafka.gradle.plugins.internal.KotlinPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class JvmLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "org.jetbrains.kotlin.jvm")
            apply<KotlinPlugin>()
            apply<CodestylePlugin>()

            // Ensure we target the correct JVM environment
            tasks.withType<KotlinCompile> {
                compilerOptions {
                    freeCompilerArgs.add("-Xjdk-release=21")
                }
            }
        }
    }
}
