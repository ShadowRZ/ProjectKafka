package io.github.shadowrz.projectkafka.libraries.data.impl.di

import android.content.Context
import androidx.sqlite.db.SupportSQLiteOpenHelper
import app.cash.sqldelight.db.SqlDriver
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.ForScope
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import io.github.shadowrz.projectkafka.libraries.data.api.System
import io.github.shadowrz.projectkafka.libraries.data.impl.systemSqliteDriver
import io.github.shadowrz.projectkafka.libraries.di.SystemScope
import io.github.shadowrz.projectkafka.libraries.di.annotations.ApplicationContext

@BindingContainer
@ContributesTo(SystemScope::class)
object SystemSqlDriverModule {
    @Provides
    @ForScope(SystemScope::class)
    @SingleIn(SystemScope::class)
    fun provideSqlDriver(
        @ApplicationContext context: Context,
        system: System,
        factory: SupportSQLiteOpenHelper.Factory,
    ): SqlDriver =
        systemSqliteDriver(
            context = context,
            system = system,
            factory = factory,
        )
}
