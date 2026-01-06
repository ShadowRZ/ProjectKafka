package io.github.shadowrz.projectkafka.libraries.data.impl

import app.cash.sqldelight.db.SqlDriver
import io.github.shadowrz.projectkafka.libraries.data.impl.db.Chat
import io.github.shadowrz.projectkafka.libraries.data.impl.db.FrontLog
import io.github.shadowrz.projectkafka.libraries.data.impl.db.GlobalDatabase
import io.github.shadowrz.projectkafka.libraries.data.impl.db.Member
import io.github.shadowrz.projectkafka.libraries.data.impl.db.Message
import io.github.shadowrz.projectkafka.libraries.data.impl.db.System
import io.github.shadowrz.projectkafka.libraries.data.impl.db.SystemDatabase
import io.github.shadowrz.projectkafka.libraries.data.impl.db.adapters.InstantAdapter
import io.github.shadowrz.projectkafka.libraries.data.impl.db.adapters.LocalDateAdapter
import io.github.shadowrz.projectkafka.libraries.data.impl.db.adapters.UriAdapter

internal fun globalDatabase(driver: SqlDriver) =
    GlobalDatabase(
        driver = driver,
        systemAdapter =
            System.Adapter(
                avatarAdapter = UriAdapter,
                coverAdapter = UriAdapter,
                lastUsedAdapter = InstantAdapter,
            ),
    )

internal fun systemDatabase(driver: SqlDriver) =
    SystemDatabase(
        driver = driver,
        chatAdapter =
            Chat.Adapter(
                avatarAdapter = UriAdapter,
            ),
        messageAdapter =
            Message.Adapter(
                mediaAdapter = UriAdapter,
                timestampAdapter = InstantAdapter,
            ),
        memberAdapter =
            Member.Adapter(
                avatarAdapter = UriAdapter,
                coverAdapter = UriAdapter,
                birthAdapter = LocalDateAdapter,
            ),
        frontLogAdapter =
            FrontLog.Adapter(
                timestampAdapter = InstantAdapter,
            ),
    )
