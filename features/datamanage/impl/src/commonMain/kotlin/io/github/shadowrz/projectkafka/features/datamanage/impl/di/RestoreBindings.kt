package io.github.shadowrz.projectkafka.features.datamanage.impl.di

import app.cash.sqldelight.db.SqlDriver
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.ForScope
import io.github.shadowrz.projectkafka.libraries.systemgraph.SystemGraphCache
import kotlinx.coroutines.CoroutineScope
import okio.FileSystem

@ContributesTo(AppScope::class)
interface RestoreBindings {
    val coroutineScope: CoroutineScope
    val fileSystem: FileSystem

    @ForScope(AppScope::class)
    val driver: SqlDriver
    val systemGraphCache: SystemGraphCache
}
