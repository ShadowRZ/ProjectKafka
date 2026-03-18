package io.github.shadowrz.projectkafka.gradle.plugins

import org.gradle.api.JavaVersion
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

/**
 * Build time metadata.
 */
object BuildMeta {
    const val APPLICATION_ID = "io.github.shadowrz.projectkafka"
    const val APPLICATION_NAME = "Project Kafka"

    const val COMPILE_SDK = 36
    const val MIN_SDK = 23
    const val TARGET_SDK = 36

    /**
     * The Baseline Java version to support.
     *
     * This is unrelated with the JVM used to run the build.
     */
    const val JAVA_VERSION = 11

    // App version
    const val VERSION_CODE = 1
    const val VERSION_NAME = "1.0"

    internal val javaVersion = JavaVersion.toVersion(JAVA_VERSION)
    internal val jvmTarget = JvmTarget.fromTarget(JAVA_VERSION.toString())

    enum class Variants(
        val applicationIdSuffix: String? = null,
        val versionNameSuffix: String? = null,
    ) {
        DEBUG(
            applicationIdSuffix = ".debug",
            versionNameSuffix = " [Debug]",
        ),
        RELEASE(),
        ;

        fun applicationName(): String = "${APPLICATION_NAME}${versionNameSuffix.orEmpty()}"
    }
}
