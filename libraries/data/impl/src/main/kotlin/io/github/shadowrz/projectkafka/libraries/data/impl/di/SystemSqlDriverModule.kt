package io.github.shadowrz.projectkafka.libraries.data.impl.di

import android.content.Context
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.ForScope
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import io.github.shadowrz.projectkafka.libraries.data.api.System
import io.github.shadowrz.projectkafka.libraries.di.SystemScope
import io.github.shadowrz.projectkafka.libraries.di.annotations.ApplicationContext
import io.github.shadowrz.projectkafka.libraries.di.annotations.SystemDirectory
import io.github.shadowrz.projectkakfa.libraries.data.impl.db.SystemDatabase
import timber.log.Timber
import java.io.File

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
        AndroidSqliteDriver(
            SystemDatabase.Schema,
            context,
            "projectkafka-${system.id}.db",
            factory = factory,
            callback =
                object : AndroidSqliteDriver.Callback(SystemDatabase.Schema) {
                    override fun onConfigure(db: SupportSQLiteDatabase) {
                        db.enableWriteAheadLogging()
                        super.onConfigure(db)
                    }
                },
        )
}
