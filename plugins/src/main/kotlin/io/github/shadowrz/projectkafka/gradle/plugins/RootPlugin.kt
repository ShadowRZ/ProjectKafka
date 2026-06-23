package io.github.shadowrz.projectkafka.gradle.plugins

import io.github.shadowrz.projectkafka.gradle.plugins.configure.koverSubprojects
import org.gradle.api.Plugin
import org.gradle.api.Project

class RootPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply(PluginIds.KOVER)

            @Suppress("UnstableApiUsage")
            koverSubprojects().forEach {
                dependencies.add("kover", dependencies.project(it))
            }
        }
    }
}
