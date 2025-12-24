package io.github.shadowrz.projectkafka.gradle.plugins

import io.github.shadowrz.projectkafka.gradle.plugins.extensions.commonLibraries
import io.github.shadowrz.projectkafka.gradle.plugins.extensions.coreLibraryDesugaring
import io.github.shadowrz.projectkafka.gradle.plugins.internal.FoundationPlugin
import libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies

class ApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "com.android.application")
            apply<FoundationPlugin>()

            dependencies {
                commonLibraries(libs)
                coreLibraryDesugaring(libs.desugar)
            }
        }
    }
}
