package io.github.shadowrz.projectkafka.features.preferences.impl

import androidx.compose.runtime.Immutable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.value.Value
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedInject
import io.github.shadowrz.hanekokoro.framework.annotations.HanekokoroInject
import io.github.shadowrz.hanekokoro.framework.integration.childComponent
import io.github.shadowrz.hanekokoro.framework.runtime.component.Component
import io.github.shadowrz.hanekokoro.framework.runtime.context.HanekokoroContext
import io.github.shadowrz.hanekokoro.framework.runtime.plugin.Plugin
import io.github.shadowrz.hanekokoro.framework.runtime.plugin.plugin
import io.github.shadowrz.projectkafka.features.preferences.api.PreferencesEntryPoint
import io.github.shadowrz.projectkafka.features.preferences.impl.root.PreferencesRootComponent
import io.github.shadowrz.projectkafka.libraries.architecture.Resolver
import kotlinx.serialization.Serializable

@AssistedInject
@HanekokoroInject.ContributesComponent(AppScope::class)
class PreferencesComponent(
    @Assisted context: HanekokoroContext,
    @Assisted plugins: List<Plugin>,
) : Component(
        context = context,
        plugins = plugins,
    ),
    Resolver<PreferencesComponent.NavTarget, PreferencesComponent.Resolved> {
    private val callback = plugin<PreferencesEntryPoint.Callback>()

    private val navigation = StackNavigation<NavTarget>()

    val childStack: Value<ChildStack<*, Resolved>> =
        childStack(
            source = navigation,
            serializer = NavTarget.serializer(),
            initialConfiguration = NavTarget.Root,
            handleBackButton = true,
            childFactory = ::resolve,
        )

    internal fun onBack() {
        onNavigateUp { }
    }

    override fun onNavigateUp(onComplete: (Boolean) -> Unit) {
        navigation.pop(onComplete)
    }

    override fun resolve(
        navTarget: NavTarget,
        componentContext: ComponentContext,
    ): Resolved =
        when (navTarget) {
            NavTarget.Root -> Resolved.Root(
                childComponent(
                    context = componentContext,
                    plugins = listOf(callback),
                ),
            )
        }

    @Immutable
    @Serializable
    sealed interface NavTarget {
        @Serializable
        data object Root : NavTarget
    }

    sealed interface Resolved {
        data class Root(
            val component: PreferencesRootComponent,
        ) : Resolved
    }
}
