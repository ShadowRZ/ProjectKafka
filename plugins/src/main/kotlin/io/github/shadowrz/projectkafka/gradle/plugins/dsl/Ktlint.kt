package io.github.shadowrz.projectkafka.gradle.plugins.dsl

import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jlleitschuh.gradle.ktlint.KtlintExtension

internal fun Project.ktlint(configure: KtlintExtension.() -> Unit) =
    pluginManager.withPlugin("org.jlleitschuh.gradle.ktlint") {
        extensions.configure<KtlintExtension>(configure)
    }
