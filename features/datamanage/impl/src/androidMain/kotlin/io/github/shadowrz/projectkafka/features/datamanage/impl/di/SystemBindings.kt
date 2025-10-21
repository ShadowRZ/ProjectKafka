package io.github.shadowrz.projectkafka.features.datamanage.impl.di

import app.cash.sqldelight.db.SqlDriver
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.ForScope
import io.github.shadowrz.projectkafka.libraries.data.api.System
import io.github.shadowrz.projectkafka.libraries.di.SystemScope

@ContributesTo(SystemScope::class)
interface SystemBindings {
    @ForScope(SystemScope::class)
    val driver: SqlDriver

    val system: System
}
