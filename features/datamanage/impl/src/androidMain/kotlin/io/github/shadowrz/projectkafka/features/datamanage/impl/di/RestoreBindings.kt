package io.github.shadowrz.projectkafka.features.datamanage.impl.di

import android.content.SharedPreferences
import app.cash.sqldelight.db.SqlDriver
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.ForScope
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import io.github.shadowrz.projectkafka.navigation.di.SystemGraphCache
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel

@ContributesTo(AppScope::class)
interface RestoreBindings {
    val coroutineScope: CoroutineScope

    val sharedPreferences: SharedPreferences

    @ForScope(AppScope::class)
    val driver: SqlDriver
    val systemGraphCache: SystemGraphCache

    val channel: Lazy<Channel<Unit>>

    @SingleIn(AppScope::class)
    @Provides
    private fun provideChannel(): Channel<Unit> = Channel()
}
