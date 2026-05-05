package io.github.shadowrz.projectkafka.gradle.plugins.internal

import com.android.build.api.dsl.CommonExtension
import io.github.shadowrz.projectkafka.gradle.plugins.ConfigurationNames
import io.github.shadowrz.projectkafka.gradle.plugins.PluginIds
import io.github.shadowrz.projectkafka.gradle.plugins.configure.addComposeDependencies
import io.github.shadowrz.projectkafka.gradle.plugins.configure.configureComposeCompiler
import io.github.shadowrz.projectkafka.gradle.plugins.extensions.libs
import org.gradle.api.Plugin
import org.gradle.api.Project

class BaseComposePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.withPlugin(PluginIds.AGP_BASE) {
                extensions.configure(CommonExtension::class.java) { buildFeatures.compose = true }
            }

            pluginManager.withPlugin(PluginIds.KOTLIN_MULTIPLATFORM) {
                dependencies.constraints {
                    addProvider(ConfigurationNames.COMMON_MAIN_IMPLEMENTATION, libs.findLibrary("compose.foundation").get())
                    addProvider(ConfigurationNames.COMMON_MAIN_IMPLEMENTATION, libs.findLibrary("compose.runtime").get())
                    addProvider(ConfigurationNames.COMMON_MAIN_IMPLEMENTATION, libs.findLibrary("compose.ui").get())
                }
            }

            pluginManager.withPlugin(PluginIds.COMPOSE_COMPILER) {
                configureComposeCompiler()
                addComposeDependencies()
            }
        }
    }
}
