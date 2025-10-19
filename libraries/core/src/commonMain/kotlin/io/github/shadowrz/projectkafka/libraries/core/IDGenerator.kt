package io.github.shadowrz.projectkafka.libraries.core

import java.util.UUID
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

object IDGenerator {
    @OptIn(ExperimentalUuidApi::class)
    fun generate() = Uuid.random().toHexString()
}
