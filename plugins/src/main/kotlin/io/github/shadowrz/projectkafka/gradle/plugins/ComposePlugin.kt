package io.github.shadowrz.projectkafka.gradle.plugins

import io.github.shadowrz.projectkafka.gradle.plugins.configure.addComposeDependencies
import io.github.shadowrz.projectkafka.gradle.plugins.internal.BaseComposePlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply

class ComposePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "org.jetbrains.kotlin.plugin.compose")
            pluginManager.apply(BaseComposePlugin::class.java)

            pluginManager.withPlugin(PluginIds.KOTLIN_MULTIPLATFORM) {
                pluginManager.apply(PluginIds.COMPOSE)
            }

            addComposeDependencies()
        }
    }
}
