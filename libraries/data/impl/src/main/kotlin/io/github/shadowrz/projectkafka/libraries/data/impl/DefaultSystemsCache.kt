package io.github.shadowrz.projectkafka.libraries.data.impl

import com.eygraber.uri.Uri
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import io.github.shadowrz.projectkafka.libraries.data.api.System
import io.github.shadowrz.projectkafka.libraries.data.api.SystemID
import io.github.shadowrz.projectkafka.libraries.data.api.SystemsCache
import io.github.shadowrz.projectkafka.libraries.data.api.SystemsStore
import java.util.concurrent.ConcurrentHashMap

@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
@Inject
class DefaultSystemsCache(
    private val systemsStore: SystemsStore,
) : SystemsCache {
    private val cache: ConcurrentHashMap<SystemID, System> = ConcurrentHashMap()

    override suspend fun get(id: SystemID): System {
        var cached = cache.get(id)
        if (cached == null) {
            val system = systemsStore.getSystem(id)
            cache.put(id, system)
            cached = system
        }
        return cached
    }

    override fun getOrNull(id: SystemID): System? = cache.get(id)
}
