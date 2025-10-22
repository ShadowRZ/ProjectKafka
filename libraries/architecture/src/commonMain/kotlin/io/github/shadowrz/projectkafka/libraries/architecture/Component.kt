package io.github.shadowrz.projectkafka.libraries.architecture

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.operator.map
import dev.zacsweers.metro.Multibinds
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.reflect.KClass

abstract class Component(
    val componentContext: ComponentContext,
    override val plugins: List<Plugin>,
) : ComponentContext by componentContext,
    PluginsOwner,
    HasParent<Component> {
    interface Factory<C : Component> {
        fun create(
            context: ComponentContext,
            parent: Component?,
            plugins: List<Plugin>,
        ): C
    }

    interface Factories {
        @Multibinds
        fun componentFactories(): Map<KClass<out Component>, Factory<*>>
    }

    protected suspend inline fun <reified Resolved : Any> Value<ChildStack<*, *>>.waitForChildAttached(): Resolved {
        val head = this.map { it.items }
        return suspendCancellableCoroutine { continuation ->
            val subscription =
                head.subscribe { backStack ->
                    val expected =
                        backStack
                            .map {
                                it.instance
                            }.filterIsInstance<Resolved>()
                            .takeIf { it.isNotEmpty() }
                            ?.last()

                    if (expected != null && !continuation.isCompleted) {
                        continuation.resume(expected)
                    }
                }

            continuation.invokeOnCancellation {
                subscription.cancel()
            }
        }
    }

    protected suspend inline fun <NavTarget : Any, reified Resolved : Any> Value<ChildStack<NavTarget, Resolved>>.waitForChildAttached(
        crossinline predicate: (NavTarget) -> Boolean,
    ): Resolved {
        val head = this.map { it.items }
        return suspendCancellableCoroutine { continuation ->
            val subscription =
                head.subscribe { backStack ->
                    val expected =
                        backStack.lastOrNull {
                            predicate(it.configuration)
                        }

                    if (expected != null && !continuation.isCompleted) {
                        continuation.resume(expected.instance)
                    }
                }

            continuation.invokeOnCancellation {
                subscription.cancel()
            }
        }
    }

    protected suspend inline fun <reified Resolved : Any> Value<ChildSlot<*, *>>.waitForChildSlot(): Resolved =
        suspendCancellableCoroutine { continuation ->
            val subscription =
                this.subscribe { slot ->
                    val expected = slot.child?.instance
                    if (expected != null && expected::class == Resolved::class && !continuation.isCompleted) {
                        continuation.resume(slot.child!!.instance as Resolved)
                    }
                }

            continuation.invokeOnCancellation {
                subscription.cancel()
            }
        }
}
