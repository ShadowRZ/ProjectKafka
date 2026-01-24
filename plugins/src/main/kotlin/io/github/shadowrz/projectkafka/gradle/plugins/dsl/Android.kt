package io.github.shadowrz.projectkafka.gradle.plugins.dsl

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import io.github.shadowrz.projectkafka.gradle.plugins.alias.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

internal fun Project.android(configure: CommonExtension.() -> Unit) {
    pluginManager.withPlugin("com.android.library") {
        extensions.configure<LibraryExtension>(configure)
    }
    pluginManager.withPlugin("com.android.application") {
        extensions.configure<ApplicationExtension>(configure)
    }
}
