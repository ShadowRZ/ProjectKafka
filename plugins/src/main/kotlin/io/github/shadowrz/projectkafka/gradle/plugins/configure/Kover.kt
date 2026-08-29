@file:Suppress("UnstableApiUsage")

package io.github.shadowrz.projectkafka.gradle.plugins.configure

import io.github.shadowrz.projectkafka.gradle.plugins.KafkaProperties
import io.github.shadowrz.projectkafka.gradle.plugins.PluginIds
import org.gradle.api.Project

internal val excludedKoverProjects =
    listOf(
        ":android-app",
        ":assets",
        ":buildmeta",
        ":desktop-app",
        ":libraries:core",
        ":libraries:di",
        ":libraries:icons",
        ":libraries:resultevents",
        ":libraries:richeditor",
        ":libraries:strings",
        ":libraries:systemgraph",
        ":libraries:uniqueid",
        ":tests:utils",
    )

internal fun Project.applyKover(kafkaProperties: KafkaProperties) {
    // Kover
    if (path !in kafkaProperties.koverExcluded.get()) {
        pluginManager.apply(PluginIds.KOVER)
    }
}
