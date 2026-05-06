package io.github.shadowrz.projectkafka.gradle.plugins.configure

import com.android.build.api.dsl.CommonExtension
import io.github.shadowrz.projectkafka.gradle.plugins.ConfigurationNames
import io.github.shadowrz.projectkafka.gradle.plugins.PluginIds
import io.github.shadowrz.projectkafka.gradle.plugins.extensions.libs
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
    val runtime = libs.findLibrary("compose.runtime").get()
    val tooling = libs.findLibrary("compose.ui.tooling").get()
    pluginManager.withPlugin(PluginIds.KOTLIN_MULTIPLATFORM) {
        extensions.configure<KotlinMultiplatformExtension> {
            sourceSets.commonMain.dependencies {
                implementation(runtime)
            }
        }

        pluginManager.withPlugin(PluginIds.AGP_LIBRARY_MULTIPLATFORM) {
            dependencies.add(ConfigurationNames.ANDROID_RUNTIME_CLASSPATH, tooling)
        }
    }

    pluginManager.withPlugin(PluginIds.AGP_BASE) {
        dependencies.add(ConfigurationNames.IMPLEMENTATION, runtime)
        dependencies.add(ConfigurationNames.DEBUG_IMPLEMENTATION, tooling)
    }
}


internal fun Project.configureCompose() {
    pluginManager.withPlugin(PluginIds.COMPOSE) {
        pluginManager.apply(PluginIds.COMPOSE_COMPILER)
    }

    pluginManager.withPlugin(PluginIds.COMPOSE_COMPILER) {
        configureComposeCompiler()
        addComposeDependencies()

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
    }

}
