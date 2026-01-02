package io.github.shadowrz.projectkafka.dependencies

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import io.github.shadowrz.projectkafka.libraries.data.api.System
import io.github.shadowrz.projectkafka.libraries.systemgraph.SystemGraphFactory

@ContributesBinding(AppScope::class)
@Inject
class DefaultSystemGraphFactory(
    private val systemGraphFactory: SystemGraph.Factory,
) : SystemGraphFactory {
    override fun create(system: System): Any = systemGraphFactory.create(system)
}
