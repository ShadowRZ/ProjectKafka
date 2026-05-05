package io.github.shadowrz.projectkafka.gradle.plugins.internal

import io.github.shadowrz.projectkafka.gradle.plugins.ConfigurationNames
import io.github.shadowrz.projectkafka.gradle.plugins.PluginIds
import io.github.shadowrz.projectkafka.gradle.plugins.extensions.libs
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
                    dependencies.add(ConfigurationNames.TEST_IMPLEMENTATION, libs.findLibrary("kotest.runner.junit6").get())
                    dependencies.add(ConfigurationNames.TEST_IMPLEMENTATION, libs.findLibrary("kotest.assertions").get())
                }

                // TODO: Add JUnit4 runner to Instrumented Tests

                pluginManager.withPlugin(PluginIds.KOTLIN_MULTIPLATFORM) {
                    // KMP modules using Kotest requires KSP
                    pluginManager.apply(PluginIds.KSP)

                    extensions.configure(KotlinMultiplatformExtension::class.java) {
                        sourceSets.commonTest.dependencies {
                            implementation(libs.findLibrary("kotest.framework.engine").get())
                            implementation(libs.findLibrary("kotest.assertions").get())
                        }
                        sourceSets.configureEach {
                            dependencies {
                                when(name) {
                                    "commonTest" -> {
                                        implementation(libs.findLibrary("kotest.framework.engine").get())
                                        implementation(libs.findLibrary("kotest.assertions").get())
                                    }
                                    "androidHostTest" -> {
                                        implementation(libs.findLibrary("kotest.runner.junit4").get())
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
