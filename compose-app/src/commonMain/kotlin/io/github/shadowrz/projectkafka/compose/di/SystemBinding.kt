package io.github.shadowrz.projectkafka.compose.di

import dev.zacsweers.metro.ContributesTo
import io.github.shadowrz.projectkafka.features.ftue.api.FtueService
import io.github.shadowrz.projectkafka.libraries.di.SystemScope

@ContributesTo(SystemScope::class)
interface SystemBinding {
    val ftueService: FtueService
}
