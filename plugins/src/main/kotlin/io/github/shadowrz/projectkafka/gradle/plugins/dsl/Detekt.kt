package io.github.shadowrz.projectkafka.gradle.plugins.dsl

import dev.detekt.gradle.extensions.DetektExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

fun Project.detekt(configure: DetektExtension.() -> Unit) = extensions.configure<DetektExtension>(configure)
