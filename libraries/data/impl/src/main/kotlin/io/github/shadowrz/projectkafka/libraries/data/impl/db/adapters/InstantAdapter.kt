package io.github.shadowrz.projectkafka.libraries.data.impl.db.adapters

import app.cash.sqldelight.ColumnAdapter
import kotlin.time.Instant

object InstantAdapter : ColumnAdapter<Instant, Long> {
    override fun decode(databaseValue: Long): Instant = Instant.fromEpochMilliseconds(databaseValue)

    override fun encode(value: Instant): Long = value.toEpochMilliseconds()
}
