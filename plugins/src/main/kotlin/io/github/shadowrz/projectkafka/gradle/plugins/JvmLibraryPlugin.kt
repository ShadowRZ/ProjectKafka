package io.github.shadowrz.projectkafka.gradle.plugins

import io.github.shadowrz.projectkafka.gradle.plugins.configure.applyCodestyle
import io.github.shadowrz.projectkafka.gradle.plugins.configure.applyKover
import io.github.shadowrz.projectkafka.gradle.plugins.configure.configureKotlin
import io.github.shadowrz.projectkafka.gradle.plugins.configure.configureNonAndroidLint
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmExtension

class JvmLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply(PluginIds.KOTLIN_JVM)
            pluginManager.apply(PluginIds.DEPENDENCY_ANALYSIS)
            pluginManager.apply(PluginIds.AGP_LINT)

            val kafkaProperties = target.objects.newInstance(KafkaProperties::class.java)

            applyCodestyle(kafkaProperties)
            applyKover(kafkaProperties)
            configureKotlin(kafkaProperties)
            configureNonAndroidLint(kafkaProperties)

            extensions.configure(KotlinJvmExtension::class.java) { kotlin ->
                kotlin.compilerOptions {
                    freeCompilerArgs.add("-Xjdk-release=${kafkaProperties.jvmTarget.get()}")
                }
            }
        }
    }
}
