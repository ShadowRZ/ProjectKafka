package io.github.shadowrz.projectkafka.libraries.data.impl.di

import android.content.Context
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.ForScope
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import io.github.shadowrz.projectkafka.libraries.di.annotations.ApplicationContext
import io.github.shadowrz.projectkakfa.libraries.data.impl.db.GlobalDatabase

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
        AndroidSqliteDriver(
            GlobalDatabase.Schema,
            context,
            "projectkakfa.db",
            factory = factory,
            callback =
                object : AndroidSqliteDriver.Callback(GlobalDatabase.Schema) {
                    override fun onConfigure(db: SupportSQLiteDatabase) {
                        db.enableWriteAheadLogging()
                        super.onConfigure(db)
                    }
                },
        )
}
