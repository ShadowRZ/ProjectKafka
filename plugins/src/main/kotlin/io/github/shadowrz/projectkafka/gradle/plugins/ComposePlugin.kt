package io.github.shadowrz.projectkafka.gradle.plugins

import io.github.shadowrz.projectkafka.gradle.plugins.configure.addComposeDependencies
import io.github.shadowrz.projectkafka.gradle.plugins.configure.configureCompose
import org.gradle.api.Plugin
import org.gradle.api.Project

class ComposePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply(PluginIds.COMPOSE_COMPILER)

            configureCompose()
            addComposeDependencies()
        }
    }
}
