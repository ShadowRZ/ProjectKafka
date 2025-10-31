package io.github.shadowrz.projectkafka.libraries.architecture

import com.arkivanov.decompose.GenericComponentContext
import dev.zacsweers.metro.Multibinds
import kotlin.reflect.KClass

interface GenericComponent<Ctx : Any> :
    GenericComponentContext<Ctx>,
    ParentOwner<GenericComponent<*>>,
    PluginsOwner {
    interface Factory<Ctx : Any> {
        fun create(
            context: Ctx,
            parent: GenericComponent<*>?,
            plugins: List<Plugin>,
        ): GenericComponent<Ctx>
    }

    interface Factories {
        @Multibinds
        fun componentFactories(): Map<KClass<out GenericComponent<*>>, Factory<*>>
    }
}
