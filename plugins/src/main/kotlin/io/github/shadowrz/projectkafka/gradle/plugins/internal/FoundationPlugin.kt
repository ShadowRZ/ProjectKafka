package io.github.shadowrz.projectkafka.gradle.plugins.internal

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply

class FoundationPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply<CodestylePlugin>()
            apply<KotlinPlugin>()
            apply<KoverPlugin>()

            pluginManager.withPlugin("com.android.library") {
                apply<AndroidPlugin>()
            }
            pluginManager.withPlugin("com.android.application") {
                apply<AndroidPlugin>()
            }
        }
    }
}
