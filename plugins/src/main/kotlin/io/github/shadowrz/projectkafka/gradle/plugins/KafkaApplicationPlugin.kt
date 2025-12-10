package io.github.shadowrz.projectkafka.gradle.plugins

import io.github.shadowrz.projectkafka.gradle.plugins.extensions.allFeaturesImpl
import io.github.shadowrz.projectkafka.gradle.plugins.extensions.allLibrariesImpl
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies

class KafkaApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "io.github.shadowrz.projectkafka.application")
            apply(plugin = "io.github.shadowrz.projectkafka.compose")

            dependencies {
                allFeaturesImpl(target)
                allLibrariesImpl(target)
            }
        }
    }
}
