package io.github.shadowrz.projectkafka.features.about.impl

sealed interface AboutEvents {
    data object OpenSourceCode : AboutEvents
}
