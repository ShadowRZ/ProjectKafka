package io.github.shadowrz.projectkafka.dependencies

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Multibinds
import io.github.shadowrz.hanekokoro.framework.runtime.component.Component
import io.github.shadowrz.hanekokoro.framework.runtime.renderer.Renderer
import io.github.shadowrz.projectkafka.libraries.di.SystemScope
import kotlin.reflect.KClass

@ContributesTo(AppScope::class)
@ContributesTo(SystemScope::class)
interface HanekokoroGraph {
    @Multibinds val componentFactories: Map<KClass<out Component>, Component.Factory<*>>

    @Multibinds val renderers: Map<KClass<out Component>, Renderer<*>>
}
