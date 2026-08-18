package io.github.shadowrz.projectkafka.libraries.uniqueid

import kotlin.concurrent.atomics.AtomicInt
import kotlin.concurrent.atomics.ExperimentalAtomicApi
import kotlin.concurrent.atomics.incrementAndFetch
import kotlin.uuid.ExperimentalUuidApi

sealed interface UniqueID {
    fun generate(): String

    data object Uuid : UniqueID {
        @OptIn(ExperimentalUuidApi::class) override fun generate(): String = kotlin.uuid.Uuid.generateV4().toHexString()
    }

    @OptIn(ExperimentalAtomicApi::class)
    data class IncrementingID(private val prefix: String = "") : UniqueID {
        private val id: AtomicInt = AtomicInt(0)

        override fun generate(): String = "$prefix${id.incrementAndFetch()}"
    }
}
