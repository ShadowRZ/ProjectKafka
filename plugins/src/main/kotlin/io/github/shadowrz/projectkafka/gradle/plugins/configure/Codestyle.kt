package io.github.shadowrz.projectkafka.gradle.plugins.configure

import dev.detekt.gradle.Detekt
import dev.detekt.gradle.extensions.DetektExtension
import io.github.shadowrz.projectkafka.gradle.plugins.ConfigurationNames
import io.github.shadowrz.projectkafka.gradle.plugins.KafkaProperties
import io.github.shadowrz.projectkafka.gradle.plugins.PluginIds
import io.github.shadowrz.projectkafka.gradle.plugins.extensions.libs
import org.gradle.api.Project
import org.gradle.api.Task

@Suppress("UnstableApiUsage")
internal fun Project.applyCodestyle(kafkaProperties: KafkaProperties) {
    pluginManager.apply(PluginIds.DETEKT)

    val root = isolated.projectDirectory

    extensions.configure(DetektExtension::class.java) { detekt ->
        detekt.buildUponDefaultConfig.set(true)
        detekt.baseline.set(kafkaProperties.detektBaseline)
        detekt.config.setFrom(kafkaProperties.lintBaseline)
    }

    dependencies.add(ConfigurationNames.DETEKT_PLUGINS, libs.findBundle("detekt.plugins").get())

    tasks.withType(Detekt::class.java).configureEach { task ->
        task.basePath.set(task.project.rootProject.projectDir.absolutePath)
        task.exclude("**/resources/**")
        task.exclude("**/build/**")
        task.exclude {
            val path = it.file.relativeTo(root.asFile).path
            path.startsWith("build/generated/")
        }
        task.reports.sarif.required.set(true)
    }

    // Configures the default detekt tasks (without type checking)
    // on all scannable source code.
    //
    // This ignores sourceSet.
    tasks.named("detekt", Detekt::class.java).configure { task ->
        task.description = "Run detekt analysis on all scanned sources"
        task.setSource(files(task.project.projectDir))

        task.include("**/*.kt")
        task.include("**/*.kts")
        task.exclude("**/resources/**")
        task.exclude("**/build/**")
        task.exclude {
            val path = it.file.relativeTo(root.asFile).path
            path.startsWith("build/generated/")
        }
        task.reports.sarif.required.set(true)
    }

    tasks.register("detektTyped", Task::class.java) { task ->
        description = "Runs all type checking Detekt tasks"

        task.dependsOn(
            task.project.tasks.named {
                it.startsWith("detektMain") || it.startsWith("detektTest")
            }
        )
    }
}
