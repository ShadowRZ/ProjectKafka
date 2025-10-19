package io.github.shadowrz.projectkafka.features.welcome.impl

sealed interface WelcomeEvents {
    data class ShowHelp(
        val visible: Boolean,
    ) : WelcomeEvents
}
