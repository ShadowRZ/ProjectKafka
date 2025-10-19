package io.github.shadowrz.projectkafka.libraries.data.api

/**
 * A cache for Systems.
 */
interface SystemsCache {
    suspend fun get(id: SystemID): System

    fun getOrNull(id: SystemID): System?
}
