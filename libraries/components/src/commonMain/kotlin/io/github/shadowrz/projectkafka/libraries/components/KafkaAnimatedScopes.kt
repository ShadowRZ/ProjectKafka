package io.github.shadowrz.projectkafka.libraries.components

import com.slack.circuit.sharedelements.SharedElementTransitionScope

interface KafkaAnimatedScopes : SharedElementTransitionScope.AnimatedScope {
    object ListDetail : KafkaAnimatedScopes
}
