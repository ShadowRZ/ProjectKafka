package io.github.shadowrz.projectkafka.libraries.data.impl.di

import app.cash.sqldelight.db.SqlDriver
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.ForScope
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import io.github.shadowrz.projectkafka.libraries.data.impl.db.SystemDatabase
import io.github.shadowrz.projectkafka.libraries.data.impl.systemDatabase
import io.github.shadowrz.projectkafka.libraries.di.SystemScope

@BindingContainer
@ContributesTo(SystemScope::class)
object SystemModule {
    @Provides
    @SingleIn(SystemScope::class)
    fun provideSystemDatabase(
        @ForScope(SystemScope::class) driver: SqlDriver,
    ): SystemDatabase = systemDatabase(driver = driver)
}
