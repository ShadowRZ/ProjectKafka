package io.github.shadowrz.projectkafka.navigation.di

import io.github.shadowrz.projectkafka.libraries.data.api.System

interface SystemGraphFactory {
    fun create(system: System): Any
}
