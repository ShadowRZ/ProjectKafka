package io.github.shadowrz.projectkafka.libraries.data.impl.di

import app.cash.sqldelight.db.SqlDriver
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.ForScope
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import io.github.shadowrz.projectkafka.libraries.data.impl.globalSqliteDriver
import io.github.shadowrz.projectkafka.libraries.di.annotations.FilesDirectory
import okio.Path

@BindingContainer
@ContributesTo(AppScope::class)
object GlobalSqlDriverModule {
    @Provides
    @ForScope(AppScope::class)
    @SingleIn(AppScope::class)
    fun provideSqlDriver(
        @FilesDirectory filesDir: Path,
    ): SqlDriver =
        globalSqliteDriver(
            file = (filesDir / "databases" / "projectkafka.db").toFile().apply { mkdirs() },
        )
}
