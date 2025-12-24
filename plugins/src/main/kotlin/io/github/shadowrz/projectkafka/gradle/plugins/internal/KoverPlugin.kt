package io.github.shadowrz.projectkafka.gradle.plugins.internal

import io.github.shadowrz.projectkafka.gradle.plugins.configure.excludedKoverProjects
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply

internal class KoverPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            if (path !in excludedKoverProjects) {
                apply(plugin = "org.jetbrains.kotlinx.kover")
            }
        }
    }
}
