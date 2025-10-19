package io.github.shadowrz.projectkafka.gradle.plugins.dsl

import org.jetbrains.compose.ComposePlugin
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

val KotlinMultiplatformExtension.compose
    get() = this.extensions.getByName("compose") as ComposePlugin.Dependencies
