package io.github.shadowrz.projectkafka.gradle.plugins

import org.gradle.api.Plugin
import org.gradle.api.Project

class RootPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply(PluginIds.KOVER)

            val kafkaProperties = target.objects.newInstance(KafkaProperties::class.java)

            @Suppress("UnstableApiUsage")
            for (project in target.subprojects) {
                if (
                    project.path !in kafkaProperties.koverExcluded.get() &&
                        project.isolated.projectDirectory.file("build.gradle.kts").asFile.exists()
                ) {
                    dependencies.add("kover", dependencies.project(project.path))
                }
            }

            tasks.register("codestyle") { task ->
                task.group = "CI"
                task.description = "Check project for codestyle problems."

                task.project.subprojects {
                    tasks.findByName("detekt")?.let { task.dependsOn(it) }
                    tasks.findByName("lint")?.let { task.dependsOn(it) }
                }
            }
        }
    }
}
