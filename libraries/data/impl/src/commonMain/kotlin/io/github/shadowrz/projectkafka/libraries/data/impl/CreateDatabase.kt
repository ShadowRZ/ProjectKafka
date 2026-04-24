package io.github.shadowrz.projectkafka.libraries.data.impl

import app.cash.sqldelight.db.SqlDriver
import io.github.shadowrz.projectkafka.libraries.data.impl.db.FrontLog
import io.github.shadowrz.projectkafka.libraries.data.impl.db.GlobalDatabase
import io.github.shadowrz.projectkafka.libraries.data.impl.db.Member
import io.github.shadowrz.projectkafka.libraries.data.impl.db.Message
import io.github.shadowrz.projectkafka.libraries.data.impl.db.System
import io.github.shadowrz.projectkafka.libraries.data.impl.db.SystemDatabase
import io.github.shadowrz.projectkafka.libraries.data.impl.db.adapters.InstantAdapter
import io.github.shadowrz.projectkafka.libraries.data.impl.db.adapters.LocalDateAdapter

internal fun globalDatabase(driver: SqlDriver) =
    GlobalDatabase(
        driver = driver,
        systemAdapter =
            System.Adapter(
                lastUsedAdapter = InstantAdapter,
            ),
    )

internal fun systemDatabase(driver: SqlDriver) =
    SystemDatabase(
        driver = driver,
        messageAdapter =
            Message.Adapter(
                timestampAdapter = InstantAdapter,
            ),
        memberAdapter =
            Member.Adapter(
                birthAdapter = LocalDateAdapter,
            ),
        frontLogAdapter =
            FrontLog.Adapter(
                timestampAdapter = InstantAdapter,
            ),
    )
