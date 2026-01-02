package io.github.shadowrz.projectkafka.libraries.systemgraph

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import io.github.shadowrz.projectkafka.libraries.data.api.System
import io.github.shadowrz.projectkafka.libraries.data.api.SystemID
import java.util.concurrent.ConcurrentHashMap

@Inject
@SingleIn(AppScope::class)
class SystemGraphCache(
    private val systemGraphFactory: SystemGraphFactory,
) {
    private val cache = ConcurrentHashMap<SystemID, Any>()

    fun getOrCreate(system: System): Any =
        cache.getOrPut(system.id) {
            systemGraphFactory.create(system)
        }

    fun graphs(): List<Any> = cache.values.toList()

    fun clear() = cache.clear()
}
