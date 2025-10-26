package io.github.shadowrz.projectkafka.features.datamanage.impl

import app.cash.sqldelight.db.SqlDriver
import java.io.File

internal fun SqlDriver.exportTo(file: File) {
    execute(
        null,
        "VACUUM main INTO ?",
        1,
    ) {
        bindString(0, file.absolutePath)
    }
}
