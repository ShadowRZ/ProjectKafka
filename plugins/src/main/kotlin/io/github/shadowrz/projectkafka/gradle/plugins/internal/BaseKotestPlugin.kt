package io.github.shadowrz.projectkafka.gradle.plugins.internal

import io.github.shadowrz.projectkafka.gradle.plugins.ConfigurationNames
import io.github.shadowrz.projectkafka.gradle.plugins.PluginIds
import libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

/** Configures Kotest. */
internal class BaseKotestPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.withPlugin("io.kotest") {
                // Ensure we use JUnit Platform
                tasks.withType(Test::class.java).configureEach { useJUnitPlatform() }

                pluginManager.withPlugin(PluginIds.AGP_BASE) {
                    dependencies.add(ConfigurationNames.TEST_IMPLEMENTATION, libs.kotest.runner.junit6)
                    dependencies.add(ConfigurationNames.TEST_IMPLEMENTATION, libs.kotest.assertions)
                }

                // TODO: Add JUnit4 runner to Instrumented Tests

                pluginManager.withPlugin(PluginIds.KOTLIN_MULTIPLATFORM) {
                    // KMP modules using Kotest requires KSP
                    pluginManager.apply(PluginIds.KSP)

                    extensions.configure(KotlinMultiplatformExtension::class.java) {
                        sourceSets.commonTest.dependencies {
                            implementation(libs.kotest.framework.engine)
                            implementation(libs.kotest.assertions)
                        }
                    }
                }
            }
        }
    }
}
