package io.github.shadowrz.projectkafka.gradle.plugins

import io.github.shadowrz.projectkafka.gradle.plugins.configure.applyCodestyle
import io.github.shadowrz.projectkafka.gradle.plugins.configure.applyKover
import io.github.shadowrz.projectkafka.gradle.plugins.configure.configureAndroid
import io.github.shadowrz.projectkafka.gradle.plugins.configure.configureKotlin
import org.gradle.api.Plugin
import org.gradle.api.Project

class LibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply(PluginIds.AGP_LIBRARY)
            pluginManager.apply(PluginIds.DEPENDENCY_ANALYSIS)

            val kafkaProperties = target.objects.newInstance(KafkaProperties::class.java)

            applyCodestyle(kafkaProperties)
            configureAndroid(kafkaProperties)
            configureKotlin(kafkaProperties)
            applyKover()
        }
    }
}
