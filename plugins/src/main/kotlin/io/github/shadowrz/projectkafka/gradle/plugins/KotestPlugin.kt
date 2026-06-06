package io.github.shadowrz.projectkafka.gradle.plugins

import io.github.shadowrz.projectkafka.gradle.plugins.configure.applyKover
import io.github.shadowrz.projectkafka.gradle.plugins.extensions.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KotestPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply(PluginIds.KOTEST)

            applyKover()

            // Ensure we use JUnit Platform
            tasks.withType(Test::class.java).configureEach { test ->
                test.useJUnitPlatform()
            }

            pluginManager.withPlugin(PluginIds.AGP_BASE) {
                dependencies.add(ConfigurationNames.TEST_IMPLEMENTATION, libs.findLibrary("kotest.runner.junit6").get())
                dependencies.add(ConfigurationNames.TEST_IMPLEMENTATION, libs.findLibrary("kotest.assertions").get())
            }

            pluginManager.withPlugin(PluginIds.KOTLIN_MULTIPLATFORM) {
                // KMP modules using Kotest requires KSP
                pluginManager.apply(PluginIds.KSP)

                extensions.configure(KotlinMultiplatformExtension::class.java) { kotlin ->
                    kotlin.sourceSets.configureEach { sourceSet ->
                        sourceSet.dependencies {
                            when (name) {
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
