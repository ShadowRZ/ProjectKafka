package io.github.shadowrz.projectkafka.libraries.architecture

interface ParentOwner<P> {
    val parent: P?
        get() = null
}
