package io.github.shadowrz.projectkafka.libraries.core.log.logger

open class LoggerTag(
    name: String,
    parent: LoggerTag? = null,
) {
    object Root : LoggerTag("ProjectKafka")

    object IntentResolver : LoggerTag("IntentResolver", Root)

    val value: String =
        if (parent == null) {
            name
        } else {
            "${parent.value}/$name"
        }
}
