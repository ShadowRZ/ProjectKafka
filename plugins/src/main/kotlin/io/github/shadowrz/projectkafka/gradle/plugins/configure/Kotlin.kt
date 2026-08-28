package io.github.shadowrz.projectkafka.gradle.plugins.configure

import io.github.shadowrz.projectkafka.gradle.plugins.KafkaProperties
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.plugins.JavaBasePlugin
import org.gradle.api.plugins.JavaPluginExtension
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompilerOptions
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

internal fun Project.configureKotlin(kafkaProperties: KafkaProperties) {
    val javaVersion = kafkaProperties.jvmTarget.map(JavaVersion::toVersion)
    val jvmTarget = kafkaProperties.jvmTarget.map { JvmTarget.fromTarget(it.toString()) }
    plugins.withType(JavaBasePlugin::class.java).configureEach {
        extensions.configure(JavaPluginExtension::class.java) { java ->
            java.sourceCompatibility = javaVersion.get()
            java.targetCompatibility = javaVersion.get()
        }
    }

    tasks.withType(KotlinCompilationTask::class.java).configureEach { task ->
        task.compilerOptions {
            progressiveMode.set(true)
            allWarningsAsErrors.set(true)
            freeCompilerArgs.addAll(
                "-Xconsistent-data-class-copy-visibility",
                "-Xexpect-actual-classes",
            )

            if (this is KotlinJvmCompilerOptions) {
                this.jvmTarget.set(jvmTarget)
            }
        }
    }
}
