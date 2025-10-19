package io.github.shadowrz.projectkafka

import android.app.Application
import dev.zacsweers.metro.createGraphFactory
import io.github.shadowrz.projectkafka.di.AppGraph
import io.github.shadowrz.projectkafka.libraries.architecture.DependencyGraphOwner
import io.github.shadowrz.projectkafka.libraries.core.log.logger.LoggerTag
import io.github.shadowrz.projectkafka.libraries.strings.CommonStrings
import timber.log.Timber

class KafkaApplication :
    Application(),
    DependencyGraphOwner {
    override var graph: AppGraph =
        createGraphFactory<AppGraph.Factory>().create(
            this,
        )
}
