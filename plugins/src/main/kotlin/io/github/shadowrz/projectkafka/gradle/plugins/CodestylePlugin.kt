package io.github.shadowrz.projectkafka.gradle.plugins

import io.github.shadowrz.projectkafka.gradle.plugins.dsl.detekt
import io.github.shadowrz.projectkafka.gradle.plugins.dsl.ktlint
import io.github.shadowrz.projectkafka.gradle.plugins.extensions.detektPlugins
import libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.assign
import org.gradle.kotlin.dsl.dependencies

class CodestylePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "io.gitlab.arturbosch.detekt")
            apply(plugin = "org.jlleitschuh.gradle.ktlint")

            ktlint {
                version = "1.7.1"
                android = true
            }

            detekt {
                buildUponDefaultConfig = true
                config.setFrom(
                    @Suppress("UnstableApiUsage")
                    isolated.rootProject.projectDirectory.file("config/detekt/detekt.yml"),
                )
            }

            dependencies {
                detektPlugins(libs.detekt.compose)
            }
        }
    }
}
