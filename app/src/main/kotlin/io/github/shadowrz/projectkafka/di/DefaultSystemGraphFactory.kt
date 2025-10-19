package io.github.shadowrz.projectkafka.di

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import io.github.shadowrz.projectkafka.libraries.data.api.System
import io.github.shadowrz.projectkafka.navigation.di.SystemGraphFactory

@ContributesBinding(AppScope::class)
@Inject
class DefaultSystemGraphFactory(
    private val appGraph: AppGraph,
) : SystemGraphFactory {
    override fun create(system: System): Any = appGraph.systemGraphFactory.create(system)
}
