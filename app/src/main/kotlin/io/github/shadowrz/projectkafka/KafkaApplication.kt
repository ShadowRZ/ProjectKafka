package io.github.shadowrz.projectkafka

import android.app.Application
import dev.zacsweers.metro.createGraphFactory
import io.github.shadowrz.projectkafka.di.AppGraph
import io.github.shadowrz.projectkafka.libraries.di.DependencyGraphOwner
import io.github.shadowrz.projectkafka.libraries.di.ResetDependencyGraph

class KafkaApplication :
    Application(),
    DependencyGraphOwner,
    ResetDependencyGraph {
    override var graph: AppGraph = createGraphFactory<AppGraph.Factory>().create(this)

    override fun resetGraph() {
        graph = createGraphFactory<AppGraph.Factory>().create(this)
    }
}
