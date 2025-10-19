package io.github.shadowrz.projectkafka.gradle.plugins

import com.android.build.api.dsl.LibraryExtension
import io.github.shadowrz.projectkafka.gradle.plugins.configure.configureAndroid
import io.github.shadowrz.projectkafka.gradle.plugins.configure.configureKotlin
import io.github.shadowrz.projectkafka.gradle.plugins.extensions.commonLibraries
import io.github.shadowrz.projectkafka.gradle.plugins.extensions.coreLibraryDesugaring
import libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.AbstractTestTask
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

class LibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "com.autonomousapps.dependency-analysis")
            apply(plugin = "io.github.shadowrz.projectkafka.codestyle")

            if (!pluginManager.hasPlugin("org.jetbrains.kotlin.multiplatform")) {
                apply(plugin = "com.android.library")
                apply(plugin = "org.jetbrains.kotlin.android")

                extensions.configure<LibraryExtension> {
                    configureAndroid()
                }

                configureKotlin<KotlinAndroidProjectExtension>()

                dependencies {
                    commonLibraries(libs)
                    coreLibraryDesugaring(libs.desugar)
                }
            }

            tasks.withType<AbstractTestTask> {
                failOnNoDiscoveredTests.set(false)
            }
        }
    }
}
