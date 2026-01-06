package io.github.shadowrz.projectkafka.libraries.data.impl

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import io.github.shadowrz.projectkafka.libraries.data.impl.db.GlobalDatabase
import io.github.shadowrz.projectkafka.libraries.data.impl.db.SystemDatabase
import java.io.File

internal fun globalSqliteDriver(file: File) =
    JdbcSqliteDriver(
        "jdbc:sqlite:${file.absolutePath}",
        schema = GlobalDatabase.Schema,
    )

internal fun systemSqliteDriver(file: File) =
    JdbcSqliteDriver(
        "jdbc:sqlite:${file.absolutePath}",
        schema = SystemDatabase.Schema,
    )
