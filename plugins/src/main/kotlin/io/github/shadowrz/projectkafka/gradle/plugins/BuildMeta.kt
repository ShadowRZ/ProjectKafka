package io.github.shadowrz.projectkafka.gradle.plugins

/**
 * Build time metadata.
 */
object BuildMeta {
    const val APPLICATION_ID = "io.github.shadowrz.projectkafka"
    const val APPLICATION_NAME = "Project Kafka"

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
