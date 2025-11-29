package io.github.shadowrz.projectkafka.gradle.plugins

import com.android.build.api.dsl.ApplicationExtension
import io.github.shadowrz.projectkafka.gradle.plugins.configure.configureAndroid
import io.github.shadowrz.projectkafka.gradle.plugins.configure.configureKotlin
import io.github.shadowrz.projectkafka.gradle.plugins.extensions.commonLibraries
import io.github.shadowrz.projectkafka.gradle.plugins.extensions.coreLibraryDesugaring
import libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

class ApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "io.github.shadowrz.projectkafka.codestyle")
            apply(plugin = "com.android.application")

            extensions.configure<ApplicationExtension> {
                configureAndroid()
            }

            if (!pluginManager.hasPlugin("org.jetbrains.kotlin.multiplatform")) {
                apply(plugin = "org.jetbrains.kotlin.android")

                configureKotlin<KotlinAndroidProjectExtension>()

                dependencies {
                    commonLibraries(libs)
                    coreLibraryDesugaring(libs.desugar)
                }
            }
        }
    }
}
