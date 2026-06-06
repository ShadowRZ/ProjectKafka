package io.github.shadowrz.projectkafka.gradle.plugins

import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryTarget
import io.github.shadowrz.projectkafka.gradle.plugins.extensions.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class FeaturePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply(PluginIds.AGP_LIBRARY_MULTIPLATFORM)
            pluginManager.apply(MultiplatformPlugin::class.java)
            pluginManager.apply(ComposePlugin::class.java)
            pluginManager.apply(PluginIds.COMPOSE)
            pluginManager.apply(PluginIds.METRO)
            pluginManager.apply(PluginIds.KSP)

            extensions.configure(KotlinMultiplatformExtension::class.java) { kotlin ->
                kotlin.targets.withType(KotlinMultiplatformAndroidLibraryTarget::class.java).configureEach { android ->
                    android.androidResources {
                        enable = true
                    }
                }

                kotlin.sourceSets.named("commonMain").configure { sourceSet ->
                    sourceSet.dependencies {
                        implementation(project(":designsystem"))
                        implementation(libs.findBundle("projectkafka.feature").get())
                    }
                }
            }
        }
    }
}
