package io.github.shadowrz.projectkafka.libraries.data.impl.di

import app.cash.sqldelight.db.SqlDriver
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.ForScope
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import io.github.shadowrz.projectkafka.libraries.data.api.System
import io.github.shadowrz.projectkafka.libraries.data.impl.systemSqliteDriver
import io.github.shadowrz.projectkafka.libraries.di.SystemScope
import io.github.shadowrz.projectkafka.libraries.di.annotations.FilesDirectory
import okio.Path

@BindingContainer
@ContributesTo(SystemScope::class)
object SystemSqlDriverModule {
    @Provides
    @ForScope(SystemScope::class)
    @SingleIn(SystemScope::class)
    fun provideSqlDriver(
        @FilesDirectory filesDir: Path,
        system: System,
    ): SqlDriver =
        systemSqliteDriver(
            file = (filesDir / "databases" / "projectkafka-${system.id}.db").toFile().apply { mkdirs() },
        )
}
