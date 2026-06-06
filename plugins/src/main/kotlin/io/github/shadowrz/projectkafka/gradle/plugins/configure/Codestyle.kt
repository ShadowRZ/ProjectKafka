package io.github.shadowrz.projectkafka.gradle.plugins.configure

import dev.detekt.gradle.Detekt
import dev.detekt.gradle.extensions.DetektExtension
import io.github.shadowrz.projectkafka.gradle.plugins.ConfigurationNames
import io.github.shadowrz.projectkafka.gradle.plugins.PluginIds
import io.github.shadowrz.projectkafka.gradle.plugins.extensions.libs
import org.gradle.api.Project

internal fun Project.applyCodestyle() {
    pluginManager.apply(PluginIds.DETEKT)

    extensions.configure(DetektExtension::class.java) { detekt ->
        detekt.buildUponDefaultConfig.set(true)
        detekt.config.setFrom(@Suppress("UnstableApiUsage") isolated.rootProject.projectDirectory.file("config/detekt/detekt.yml"))
    }

    dependencies.add(ConfigurationNames.DETEKT_PLUGINS, libs.findBundle("detekt.plugins").get())

    tasks.withType(Detekt::class.java).configureEach { detekt ->
        @Suppress("UnstableApiUsage") val root = isolated.projectDirectory

        detekt.exclude {
            val path = it.file.relativeTo(root.asFile).path
            path.startsWith("build/generated/")
        }
    }
}
