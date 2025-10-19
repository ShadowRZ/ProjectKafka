package io.github.shadowrz.projectkafka.gradle.plugins.dsl

import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jlleitschuh.gradle.ktlint.KtlintExtension

fun Project.ktlint(configure: KtlintExtension.() -> Unit) = extensions.configure<KtlintExtension>(configure)
