package io.github.shadowrz.projectkafka.gradle.plugins.internal

import io.github.shadowrz.projectkafka.gradle.plugins.configure.configureCompose
import io.github.shadowrz.projectkafka.gradle.plugins.configure.configureComposeCompiler
import io.github.shadowrz.projectkafka.gradle.plugins.dsl.android
import io.github.shadowrz.projectkafka.gradle.plugins.extensions.implementation
import libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies

class BaseComposePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "org.jetbrains.kotlin.plugin.compose")

            android {
                configureCompose()
            }

            pluginManager.withPlugin("org.jetbrains.kotlin.multiplatform") {
                apply(plugin = "org.jetbrains.compose")

                dependencies {
                    constraints {
                        "commonMainImplementation"(libs.compose.foundation)
                        "commonMainImplementation"(libs.compose.runtime)
                        "commonMainImplementation"(libs.compose.ui)
                    }
                }
            }

            pluginManager.withPlugin("org.jetbrains.kotlin.jvm") {
                apply(plugin = "org.jetbrains.compose")
            }

            configureComposeCompiler()
        }
    }
}
