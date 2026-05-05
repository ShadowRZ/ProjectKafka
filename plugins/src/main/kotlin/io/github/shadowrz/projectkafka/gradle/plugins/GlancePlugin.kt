package io.github.shadowrz.projectkafka.gradle.plugins

import io.github.shadowrz.projectkafka.gradle.plugins.internal.BaseComposePlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply

class GlancePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply<BaseComposePlugin>()
        }
    }
}
