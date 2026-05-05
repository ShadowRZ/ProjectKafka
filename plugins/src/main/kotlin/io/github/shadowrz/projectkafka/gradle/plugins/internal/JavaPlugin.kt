package io.github.shadowrz.projectkafka.gradle.plugins.internal

import io.github.shadowrz.projectkafka.gradle.plugins.BuildMeta
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaBasePlugin
import org.gradle.api.plugins.JavaPluginExtension

/** Configures Java projects. */
internal class JavaPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.plugins.withType(JavaBasePlugin::class.java).configureEach {
            target.extensions.configure(JavaPluginExtension::class.java) {
                sourceCompatibility = BuildMeta.javaVersion
                targetCompatibility = BuildMeta.javaVersion
            }
        }
    }
}
