package io.github.shadowrz.projectkafka.libraries.architecture

import io.github.shadowrz.hanekokoro.framework.runtime.ParentOwner

/**
 * Provides [onBack] handler for usage in UI initiated back navigation.
 */
interface OnBackCallbackOwner {
    /**
     * Attempts to do back navigation similar to using back button.
     *
     * By default tries to call parent's [onBack], if any.
     * Components with navigation models should override it and perform appropriate back navigation.
     */
    fun onBack() {
        ((this as? ParentOwner<*>)?.parent as? OnBackCallbackOwner)?.onBack()
    }
}
