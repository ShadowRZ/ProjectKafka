package io.github.shadowrz.projectkafka.libraries.data.impl.di

import android.content.Context
import androidx.sqlite.db.SupportSQLiteOpenHelper
import app.cash.sqldelight.db.SqlDriver
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.ForScope
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import io.github.shadowrz.projectkafka.libraries.data.impl.globalSqliteDriver
import io.github.shadowrz.projectkafka.libraries.di.annotations.ApplicationContext

@BindingContainer
@ContributesTo(AppScope::class)
object GlobalSqlDriverModule {
    @Provides
    @ForScope(AppScope::class)
    @SingleIn(AppScope::class)
    fun provideSqlDriver(
        @ApplicationContext context: Context,
        factory: SupportSQLiteOpenHelper.Factory,
    ): SqlDriver =
        globalSqliteDriver(
            context = context,
            factory = factory,
        )
}
