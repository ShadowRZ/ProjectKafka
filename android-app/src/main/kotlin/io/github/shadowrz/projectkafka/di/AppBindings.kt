package io.github.shadowrz.projectkafka.di

import io.github.shadowrz.projectkafka.compose.KafkaApp

interface AppBindings {

    val kafkaApp: KafkaApp
}
