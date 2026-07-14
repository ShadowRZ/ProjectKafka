package io.github.shadowrz.projectkafka.gradle.plugins

import io.github.shadowrz.projectkafka.gradle.plugins.configure.excludedKoverProjects
import org.gradle.api.Plugin
import org.gradle.api.Project

class RootPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply(PluginIds.KOVER)

            @Suppress("UnstableApiUsage")
            for (project in target.subprojects) {
                if (project.path !in excludedKoverProjects && project.isolated.projectDirectory.file("build.gradle.kts").asFile.exists()) {
                    dependencies.add("kover", dependencies.project(project.path))
                }
            }
        }
    }
}
