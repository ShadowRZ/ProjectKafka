package io.github.shadowrz.projectkafka.features.datamanage.impl.di

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import io.github.shadowrz.projectkafka.libraries.core.coroutine.CoroutineDispatchers
import kotlinx.coroutines.CoroutineScope

@ContributesTo(AppScope::class)
interface RestoreBindings {
    val appCoroutineScope: CoroutineScope
    val coroutineDispatchers: CoroutineDispatchers
}
