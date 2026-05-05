package io.github.shadowrz.projectkafka.gradle.plugins.configure

import io.github.shadowrz.projectkafka.gradle.plugins.ConfigurationNames
import io.github.shadowrz.projectkafka.gradle.plugins.PluginIds
import libs
import org.gradle.api.Project
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradlePluginExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

@Suppress("UnstableApiUsage")
internal fun Project.configureComposeCompiler() {
    extensions.configure<ComposeCompilerGradlePluginExtension> {
        fun Provider<String>.onlyIfTrue() = flatMap { provider { it.takeIf(String::toBoolean) } }

        fun Provider<*>.relativeToRootProject(dir: String) =
            map {
                isolated.rootProject.projectDirectory
                    .dir("build")
                    .dir(dir)
                    .dir(projectDir.toRelativeString(rootDir))
            }

        project.providers
            .gradleProperty("io.github.shadowrz.projectkafka.compose.reports")
            .onlyIfTrue()
            .relativeToRootProject("compose/reports")
            .also(metricsDestination::set)
            .also(reportsDestination::set)

        stabilityConfigurationFiles
            .add(isolated.rootProject.projectDirectory.file("config/compose/compose.conf"))
    }
}

internal fun Project.addComposeDependencies() {
    pluginManager.withPlugin(PluginIds.KOTLIN_MULTIPLATFORM) {
        extensions.configure<KotlinMultiplatformExtension> {
            sourceSets.commonMain.dependencies {
                implementation(libs.compose.runtime)
            }
        }

        pluginManager.withPlugin(PluginIds.AGP_LIBRARY_MULTIPLATFORM) {
            dependencies.add(ConfigurationNames.ANDROID_RUNTIME_CLASSPATH, libs.compose.ui.tooling)
        }
    }

    pluginManager.withPlugin(PluginIds.AGP_BASE) {
        dependencies.add(ConfigurationNames.IMPLEMENTATION, libs.compose.runtime)
        dependencies.add(ConfigurationNames.DEBUG_IMPLEMENTATION, libs.compose.ui.tooling)
    }
}
