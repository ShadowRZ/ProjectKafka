package io.github.shadowrz.projectkafka.navigation.di

import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.ForScope
import io.github.shadowrz.hanekokoro.framework.integration.HanekokoroApp
import io.github.shadowrz.projectkafka.libraries.di.SystemScope

@ContributesTo(SystemScope::class)
interface SystemBinding {
    @ForScope(SystemScope::class)
    val hanekokoroApp: HanekokoroApp
}
