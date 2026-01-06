package io.github.shadowrz.projectkafka.libraries.data.impl

import android.content.Context
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import io.github.shadowrz.projectkafka.libraries.data.api.System
import io.github.shadowrz.projectkafka.libraries.data.impl.db.GlobalDatabase
import io.github.shadowrz.projectkafka.libraries.data.impl.db.SystemDatabase
import java.io.File

internal fun systemSqliteDriver(
    context: Context,
    file: File,
    factory: SupportSQLiteOpenHelper.Factory,
) = AndroidSqliteDriver(
    SystemDatabase.Schema,
    context,
    file.absolutePath,
    factory = factory,
    callback =
        object : AndroidSqliteDriver.Callback(SystemDatabase.Schema) {
            override fun onConfigure(db: SupportSQLiteDatabase) {
                db.enableWriteAheadLogging()
                super.onConfigure(db)
            }
        },
)

internal fun systemSqliteDriver(
    context: Context,
    system: System,
    factory: SupportSQLiteOpenHelper.Factory,
) = AndroidSqliteDriver(
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

internal fun globalSqliteDriver(
    context: Context,
    file: File,
    factory: SupportSQLiteOpenHelper.Factory,
) = AndroidSqliteDriver(
    GlobalDatabase.Schema,
    context,
    file.absolutePath,
    factory = factory,
    callback =
        object : AndroidSqliteDriver.Callback(GlobalDatabase.Schema) {
            override fun onConfigure(db: SupportSQLiteDatabase) {
                db.enableWriteAheadLogging()
                super.onConfigure(db)
            }
        },
)

internal fun globalSqliteDriver(
    context: Context,
    factory: SupportSQLiteOpenHelper.Factory,
) = AndroidSqliteDriver(
    GlobalDatabase.Schema,
    context,
    "projectkafka.db",
    factory = factory,
    callback =
        object : AndroidSqliteDriver.Callback(GlobalDatabase.Schema) {
            override fun onConfigure(db: SupportSQLiteDatabase) {
                db.enableWriteAheadLogging()
                super.onConfigure(db)
            }
        },
)
