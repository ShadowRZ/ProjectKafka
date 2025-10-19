package io.github.shadowrz.projectkafka.libraries.architecture

interface HasParent<P> {
    val parent: P?
        get() = null
}
