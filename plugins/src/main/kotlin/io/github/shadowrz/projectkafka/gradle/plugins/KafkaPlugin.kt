package io.github.shadowrz.projectkafka.gradle.plugins

import io.github.shadowrz.projectkafka.gradle.plugins.configure.applyCodestyle
import io.github.shadowrz.projectkafka.gradle.plugins.configure.configureAndroid
import io.github.shadowrz.projectkafka.gradle.plugins.configure.configureCompose
import io.github.shadowrz.projectkafka.gradle.plugins.configure.configureSourceCompile
import io.github.shadowrz.projectkafka.gradle.plugins.configure.excludedKoverProjects
import org.gradle.api.Plugin
import org.gradle.api.Project

class KafkaPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            // DAGP
            pluginManager.apply(PluginIds.DEPENDENCY_ANALYSIS)

            applyKover()
            applyCodestyle()
            configureSourceCompile()
            configureAndroid()
            configureCompose()
        }
    }

    internal fun Project.applyKover() {
        // Kover
        if (path !in excludedKoverProjects) {
            pluginManager.apply(PluginIds.KOVER)
        }
    }
}
