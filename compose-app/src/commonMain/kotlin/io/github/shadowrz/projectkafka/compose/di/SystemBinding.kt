package io.github.shadowrz.projectkafka.compose.di

import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.ForScope
import io.github.shadowrz.projectkafka.features.ftue.api.FtueService
import io.github.shadowrz.projectkafka.libraries.data.api.MembersStore
import io.github.shadowrz.projectkafka.libraries.di.SystemScope
import kotlinx.coroutines.CoroutineScope

@ContributesTo(SystemScope::class)
interface SystemBinding {
    val ftueService: FtueService
    @ForScope(SystemScope::class) val coroutineScope: CoroutineScope
    val membersStore: MembersStore
}
