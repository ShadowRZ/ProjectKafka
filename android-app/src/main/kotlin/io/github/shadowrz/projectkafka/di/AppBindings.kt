package io.github.shadowrz.projectkafka.di

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import io.github.shadowrz.projectkafka.compose.KafkaApp

@ContributesTo(AppScope::class)
interface AppBindings {

    val kafkaApp: KafkaApp
}
