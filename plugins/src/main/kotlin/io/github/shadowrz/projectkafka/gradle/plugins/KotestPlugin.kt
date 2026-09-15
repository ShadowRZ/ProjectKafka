package io.github.shadowrz.projectkafka.gradle.plugins

import io.github.shadowrz.projectkafka.gradle.plugins.configure.applyKover
import io.github.shadowrz.projectkafka.gradle.plugins.extensions.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.process.CommandLineArgumentProvider
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KotestPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply(PluginIds.KOTEST)

            val kafkaProperties = target.objects.newInstance(KafkaProperties::class.java)

            applyKover(kafkaProperties)

            // Ensure we use JUnit Platform
            tasks.withType(Test::class.java).configureEach { test ->
                test.useJUnitPlatform()

                test.jvmArgumentProviders += CommandLineArgumentProvider {
                    listOf("--enable-native-access=ALL-UNNAMED")
                }
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
                            when (sourceSet.name) {
                                "commonTest" -> {
                                    implementation(libs.findLibrary("kotest.framework.engine").get())
                                    implementation(libs.findLibrary("kotest.assertions").get())
                                }

                                "jvmTest" -> {
                                    implementation(libs.findLibrary("kotest.runner.junit6").get())
                                }

                                // While androidHostTest should not need to use JUnit 4, this serves as a failsafe
                                "androidHostTest",
                                "androidDeviceTest" -> {
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
