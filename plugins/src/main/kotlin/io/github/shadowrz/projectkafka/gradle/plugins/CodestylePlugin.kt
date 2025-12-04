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
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

class CodestylePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "dev.detekt")
            apply(plugin = "org.jlleitschuh.gradle.ktlint")

            ktlint {
                version = libs.versions.ktlint
                    .asProvider()
                    .get()
                android = true

                verbose = true
                reporters {
                    reporter(ReporterType.PLAIN)
                    // For Danger
                    reporter(ReporterType.CHECKSTYLE)
                }
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
