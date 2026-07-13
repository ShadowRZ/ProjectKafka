package io.github.shadowrz.projectkafka.compose.di

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Multibinds
import io.github.shadowrz.hanekokoro.framework.runtime.component.Component
import io.github.shadowrz.hanekokoro.framework.runtime.renderer.Renderer
import io.github.shadowrz.projectkafka.libraries.architecture.NavEntryProvider
import io.github.shadowrz.projectkafka.libraries.di.SystemScope
import kotlin.reflect.KClass

@ContributesTo(AppScope::class)
@ContributesTo(SystemScope::class)
interface HanekokoroGraph {
    @Multibinds(allowEmpty = true) val componentFactories: Map<KClass<out Component>, Component.Factory<*>>

    @Multibinds(allowEmpty = true) val renderers: Map<KClass<out Component>, Renderer<*>>

    @Multibinds(allowEmpty = false) val entryProviders: Set<NavEntryProvider>
}
