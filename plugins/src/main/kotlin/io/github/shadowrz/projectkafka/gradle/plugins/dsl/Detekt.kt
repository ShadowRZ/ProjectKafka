package io.github.shadowrz.projectkafka.gradle.plugins.dsl

import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

fun Project.detekt(configure: DetektExtension.() -> Unit) = extensions.configure<DetektExtension>(configure)
