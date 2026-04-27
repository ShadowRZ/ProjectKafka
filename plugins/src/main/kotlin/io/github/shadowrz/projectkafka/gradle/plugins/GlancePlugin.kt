package io.github.shadowrz.projectkafka.gradle.plugins

import io.github.shadowrz.projectkafka.gradle.plugins.configure.addGlanceDependencies
import io.github.shadowrz.projectkafka.gradle.plugins.configure.configureCompose
import io.github.shadowrz.projectkafka.gradle.plugins.configure.configureComposeCompiler
import io.github.shadowrz.projectkafka.gradle.plugins.dsl.android
import io.github.shadowrz.projectkafka.gradle.plugins.internal.BaseComposePlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply

class GlancePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply<BaseComposePlugin>()

            addGlanceDependencies()
        }
    }
}
