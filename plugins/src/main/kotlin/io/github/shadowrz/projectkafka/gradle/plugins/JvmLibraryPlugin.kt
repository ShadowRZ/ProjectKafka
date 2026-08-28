package io.github.shadowrz.projectkafka.gradle.plugins

import io.github.shadowrz.projectkafka.gradle.plugins.configure.applyCodestyle
import io.github.shadowrz.projectkafka.gradle.plugins.configure.applyKover
import io.github.shadowrz.projectkafka.gradle.plugins.configure.configureKotlin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmExtension

class JvmLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply(PluginIds.KOTLIN_JVM)
            pluginManager.apply(PluginIds.DEPENDENCY_ANALYSIS)

            val kafkaProperties = target.objects.newInstance(KafkaProperties::class.java)

            applyCodestyle(kafkaProperties)
            applyKover()
            configureKotlin(kafkaProperties)

            extensions.configure(KotlinJvmExtension::class.java) { kotlin ->
                kotlin.compilerOptions {
                    freeCompilerArgs.add("-Xjdk-release=${kafkaProperties.jvmTarget.get()}")
                }
            }
        }
    }
}
