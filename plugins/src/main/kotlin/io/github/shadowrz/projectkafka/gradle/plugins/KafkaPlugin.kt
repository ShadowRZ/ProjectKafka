package io.github.shadowrz.projectkafka.gradle.plugins

import io.github.shadowrz.projectkafka.gradle.plugins.internal.AndroidPlugin
import io.github.shadowrz.projectkafka.gradle.plugins.internal.BaseComposePlugin
import io.github.shadowrz.projectkafka.gradle.plugins.internal.CodestylePlugin
import io.github.shadowrz.projectkafka.gradle.plugins.internal.JavaPlugin
import io.github.shadowrz.projectkafka.gradle.plugins.internal.KotlinPlugin
import io.github.shadowrz.projectkafka.gradle.plugins.internal.KoverPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project

class KafkaPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply(PluginIds.DEPENDENCY_ANALYSIS)
            pluginManager.apply(CodestylePlugin::class.java)
            pluginManager.apply(JavaPlugin::class.java)
            pluginManager.apply(KotlinPlugin::class.java)
            pluginManager.apply(KoverPlugin::class.java)
            pluginManager.apply(AndroidPlugin::class.java)

            pluginManager.withPlugin(PluginIds.COMPOSE_COMPILER) { pluginManager.apply(BaseComposePlugin::class.java) }
        }
    }
}
