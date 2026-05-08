package io.github.shadowrz.projectkafka.gradle.plugins

import io.github.shadowrz.projectkafka.gradle.plugins.configure.applyCodestyle
import io.github.shadowrz.projectkafka.gradle.plugins.configure.configureKotlin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmExtension

class JvmLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply(PluginIds.KOTLIN_JVM)

            applyCodestyle()
            configureKotlin()

            extensions.configure(KotlinJvmExtension::class.java) {
                compilerOptions {
                    freeCompilerArgs.add("-Xjdk-release=${BuildMeta.JAVA_VERSION}")
                }
            }
        }
    }
}
