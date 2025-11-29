package io.github.shadowrz.projectkafka.gradle.plugins

import io.github.shadowrz.projectkafka.gradle.plugins.configure.configureKotlin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension

class JvmLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "io.github.shadowrz.projectkafka.codestyle")
            apply(plugin = "org.jetbrains.kotlin.jvm")

            configureKotlin<KotlinJvmProjectExtension>()
        }
    }
}
