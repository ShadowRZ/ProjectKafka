package io.github.shadowrz.projectkafka.libraries.data.impl.di

import app.cash.sqldelight.db.SqlDriver
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.ForScope
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import io.github.shadowrz.projectkafka.libraries.data.impl.globalDatabase
import io.github.shadowrz.projectkakfa.libraries.data.impl.db.GlobalDatabase

@BindingContainer
@ContributesTo(AppScope::class)
object GlobalModule {
    @Provides
    @SingleIn(AppScope::class)
    fun provideGlobalDatabase(
        @ForScope(AppScope::class) driver: SqlDriver,
    ): GlobalDatabase = globalDatabase(driver = driver)
}
