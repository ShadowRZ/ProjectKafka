package io.github.shadowrz.projectkafka.gradle.plugins

import io.github.shadowrz.projectkafka.gradle.plugins.extensions.commonLibraries
import io.github.shadowrz.projectkafka.gradle.plugins.extensions.coreLibraryDesugaring
import io.github.shadowrz.projectkafka.gradle.plugins.internal.AndroidPlugin
import io.github.shadowrz.projectkafka.gradle.plugins.internal.KoverPlugin
import libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.AbstractTestTask
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType

class LibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "io.github.shadowrz.projectkafka.codestyle")
            apply(plugin = "com.android.library")
            apply<AndroidPlugin>()
            apply<KoverPlugin>()

            dependencies {
                commonLibraries(libs)
                coreLibraryDesugaring(libs.desugar)
            }

            tasks.withType<AbstractTestTask>().configureEach {
                failOnNoDiscoveredTests.set(false)
            }
        }
    }
}
