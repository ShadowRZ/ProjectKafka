package io.github.shadowrz.projectkafka.gradle.plugins

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import io.github.shadowrz.projectkafka.gradle.plugins.configure.addComposeDependencies
import io.github.shadowrz.projectkafka.gradle.plugins.configure.configureCompose
import io.github.shadowrz.projectkafka.gradle.plugins.configure.configureComposeCompiler
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure

class ComposePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "org.jetbrains.kotlin.plugin.compose")

            pluginManager.withPlugin("com.android.library") {
                extensions.configure<LibraryExtension> {
                    configureCompose()
                }
            }
            pluginManager.withPlugin("com.android.application") {
                extensions.configure<ApplicationExtension> {
                    configureCompose()
                }
            }

            pluginManager.withPlugin("org.jetbrains.kotlin.multiplatform") {
                apply(plugin = "org.jetbrains.compose")
            }

            configureComposeCompiler()
            addComposeDependencies()
        }
    }
}
