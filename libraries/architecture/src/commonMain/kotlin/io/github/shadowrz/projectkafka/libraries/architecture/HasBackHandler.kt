package io.github.shadowrz.projectkafka.libraries.architecture

interface HasBackHandler {
    fun onBack() {
        ((this as? HasParent<*>)?.parent as? HasBackHandler)?.onBack()
    }
}
