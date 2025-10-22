package io.github.shadowrz.projectkafka.libraries.di

/**
 * DI Graph owner.
 */
interface DependencyGraphOwner {
    /**
     * Either a graph object or a list of graphs.
     */
    val graph: Any
}
