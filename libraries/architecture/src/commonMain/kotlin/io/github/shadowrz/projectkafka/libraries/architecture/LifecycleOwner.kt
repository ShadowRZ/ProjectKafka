package io.github.shadowrz.projectkafka.libraries.architecture

import androidx.lifecycle.Lifecycle as JetpackLifecycle
import androidx.lifecycle.LifecycleOwner as JetpackLifecycleOwner
import androidx.lifecycle.LifecycleRegistry as JetpackLifecycleRegistry
import com.arkivanov.essenty.lifecycle.Lifecycle as EssentyLifecycle
import com.arkivanov.essenty.lifecycle.LifecycleOwner as EssentyLifecycleOwner

/**
 * Creates a [androidx.lifecycle.LifecycleOwner] from an existing [com.arkivanov.essenty.lifecycle.LifecycleOwner].
 * */
fun EssentyLifecycleOwner.lifecycleOwner(): JetpackLifecycleOwner = DelegateLifecycleOwner(this)

private class DelegateLifecycleOwner(
    delegate: EssentyLifecycleOwner,
) : JetpackLifecycleOwner {
    private val registry = JetpackLifecycleRegistry(this)

    private val callbacks = Callbacks()

    override val lifecycle: JetpackLifecycle = registry

    init {
        delegate.lifecycle.subscribe(callbacks)
    }

    inner class Callbacks : EssentyLifecycle.Callbacks {
        override fun onCreate() {
            registry.handleLifecycleEvent(JetpackLifecycle.Event.ON_CREATE)
        }

        override fun onStart() {
            registry.handleLifecycleEvent(JetpackLifecycle.Event.ON_START)
        }

        override fun onResume() {
            registry.handleLifecycleEvent(JetpackLifecycle.Event.ON_RESUME)
        }

        override fun onPause() {
            registry.handleLifecycleEvent(JetpackLifecycle.Event.ON_PAUSE)
        }

        override fun onStop() {
            registry.handleLifecycleEvent(JetpackLifecycle.Event.ON_STOP)
        }

        override fun onDestroy() {
            registry.handleLifecycleEvent(JetpackLifecycle.Event.ON_DESTROY)
        }
    }
}
