package io.github.shadowrz.projectkafka.gradle.plugins.dsl

import kotlinx.kover.gradle.plugin.dsl.KoverProjectExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

internal fun Project.kover(configure: KoverProjectExtension.() -> Unit) =
    pluginManager.withPlugin("org.jetbrains.kotlinx.kover") {
        extensions.configure<KoverProjectExtension>(configure)
    }
