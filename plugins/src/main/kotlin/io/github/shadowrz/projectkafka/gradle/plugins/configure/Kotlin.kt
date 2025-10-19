package io.github.shadowrz.projectkafka.gradle.plugins.configure

import Versions
import org.gradle.api.Project
import org.gradle.kotlin.dsl.assign
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinBaseExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal inline fun <reified T : KotlinBaseExtension> Project.configureKotlin() =
    configure<T> {
        when (this) {
            is KotlinAndroidProjectExtension -> compilerOptions
            is KotlinJvmProjectExtension -> compilerOptions
            is KotlinMultiplatformExtension -> compilerOptions
            else -> TODO("Unsupported project extension $this ${T::class}")
        }.apply {
            jvmToolchain {
                languageVersion = Versions.javaLanguageVersion
            }
            compilerOptions {
                allWarningsAsErrors = true
            }
            freeCompilerArgs.add("-Xannotation-default-target=param-property")
            freeCompilerArgs.add("-Xconsistent-data-class-copy-visibility")
        }
    }
