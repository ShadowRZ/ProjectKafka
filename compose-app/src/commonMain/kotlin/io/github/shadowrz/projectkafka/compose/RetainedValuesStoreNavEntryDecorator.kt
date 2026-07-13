package io.github.shadowrz.projectkafka.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.retain.RetainedValuesStoreRegistry
import androidx.compose.runtime.retain.retainRetainedValuesStoreRegistry
import androidx.navigation3.runtime.NavEntryDecorator

/**
 * Returns a [RetainedValuesStoreNavEntryDecorator] that is remembered across recompositions backed by [registry].
 *
 * The underlying storage is controlled by the provided [registry]. By default, a new [RetainedValuesStoreRegistry] is retained at this
 * point in the composition hierarchy and will be destroyed when the composition is permanently discarded or when the returned decorator is
 * removed from the composition hierarchy. If you need the backing storage of this decorator to have a different lifespan, you can manually
 * manage and provide a [RetainedValuesStoreRegistry] with the intended lifespan.
 *
 * @param registry The underlying [RetainedValuesStoreRegistry] used to provide [RetainedValuesStore] instances to [NavEntries][NavEntry].
 *   This instance should be retained to properly survive destruction and recreation scenarios.
 */
@Composable
fun <T : Any> rememberRetainedValuesStoreNavEntryDecorator(
    registry: RetainedValuesStoreRegistry = retainRetainedValuesStoreRegistry()
): RetainedValuesStoreNavEntryDecorator<T> {
    return remember(registry) {
        RetainedValuesStoreNavEntryDecorator(registry)
    }
}

/**
 * Provides the content of each [NavEntry] with a dedicated [RetainedValuesStore] so that each nav entry may retain its own values.
 *
 * @param registry The underlying [RetainedValuesStoreRegistry] used to provide [RetainedValuesStore] instances to [NavEntries][NavEntry].
 *   This instance should be retained to properly survive destruction and recreation scenarios.
 */
class RetainedValuesStoreNavEntryDecorator<T : Any>(registry: RetainedValuesStoreRegistry) :
    NavEntryDecorator<T>(
        onPop = { key ->
            registry.clearChild(key)
        },
        decorate = { entry ->
            registry.LocalRetainedValuesStoreProvider(entry.contentKey) { entry.Content() }
        },
    )
