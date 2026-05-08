package io.github.shadowrz.projectkafka.gradle.plugins.configure

import dev.detekt.gradle.Detekt
import dev.detekt.gradle.extensions.DetektExtension
import io.github.shadowrz.projectkafka.gradle.plugins.ConfigurationNames
import io.github.shadowrz.projectkafka.gradle.plugins.PluginIds
import io.github.shadowrz.projectkafka.gradle.plugins.extensions.libs
import org.gradle.api.Project
import org.jlleitschuh.gradle.ktlint.KtlintExtension
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

internal fun Project.applyCodestyle() {
    pluginManager.apply(PluginIds.DETEKT)
    pluginManager.apply(PluginIds.KTLINT)

    extensions.configure(KtlintExtension::class.java) {
        version.set(libs.findVersion("ktlint").get().toString())
        android.set(true)

        verbose.set(true)
        reporters {
            reporter(ReporterType.PLAIN)
            // For Danger
            reporter(ReporterType.CHECKSTYLE)
        }
    }

    extensions.configure(DetektExtension::class.java) {
        buildUponDefaultConfig.set(true)
        config.setFrom(
            @Suppress("UnstableApiUsage")
            isolated.rootProject.projectDirectory.file("config/detekt/detekt.yml"),
        )
    }

    dependencies.add(ConfigurationNames.DETEKT_PLUGINS, libs.findBundle("detekt.plugins").get())

    tasks.withType(Detekt::class.java).configureEach {
        @Suppress("UnstableApiUsage")
        val root = isolated.projectDirectory

        exclude {
            val path = it.file.relativeTo(root.asFile).path
            path.startsWith("build/generated/")
        }
    }
}
