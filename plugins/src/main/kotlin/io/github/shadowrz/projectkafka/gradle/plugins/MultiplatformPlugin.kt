package io.github.shadowrz.projectkafka.gradle.plugins

import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryTarget
import io.github.shadowrz.projectkafka.gradle.plugins.configure.applyCodestyle
import io.github.shadowrz.projectkafka.gradle.plugins.configure.configureKotlin
import io.github.shadowrz.projectkafka.gradle.plugins.extensions.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.targets.jvm.KotlinJvmTarget

class MultiplatformPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply(PluginIds.KOTLIN_MULTIPLATFORM)

            applyCodestyle()
            configureKotlin()

            extensions.configure(KotlinMultiplatformExtension::class.java) { kotlin ->
                kotlin.applyDefaultHierarchyTemplate()

                kotlin.targets.withType(KotlinJvmTarget::class.java).configureEach { target ->
                    target.compilerOptions {
                        freeCompilerArgs.add("-Xjdk-release=${BuildMeta.JAVA_VERSION}")
                    }
                }

                kotlin.targets.withType(KotlinMultiplatformAndroidLibraryTarget::class.java).configureEach { target ->
                    target.compileSdk = BuildMeta.COMPILE_SDK
                    target.minSdk = BuildMeta.MIN_SDK

                    target.enableCoreLibraryDesugaring = true
                }
            }

            pluginManager.withPlugin(PluginIds.AGP_LIBRARY_MULTIPLATFORM) {
                val desugar = libs.findLibrary("desugar").get()

                dependencies.add(ConfigurationNames.CORE_LIBRARY_DESUGARING, desugar)
            }

            pluginManager.withPlugin(PluginIds.COMPOSE) {
                pluginManager.apply(PluginIds.COMPOSE_COMPILER)
            }
        }
    }
}
