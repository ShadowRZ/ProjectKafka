package io.github.shadowrz.projectkafka.libraries.data.impl.db.adapters

import app.cash.sqldelight.ColumnAdapter
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Instant

object LocalDateAdapter : ColumnAdapter<LocalDate, Long> {
    override fun decode(databaseValue: Long): LocalDate = Instant.fromEpochMilliseconds(databaseValue).toLocalDateTime(TimeZone.UTC).date

    override fun encode(value: LocalDate): Long = value.atStartOfDayIn(TimeZone.UTC).toEpochMilliseconds()
}
