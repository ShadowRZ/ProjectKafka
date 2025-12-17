package io.github.shadowrz.projectkafka.di

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.ForScope
import io.github.shadowrz.hanekokoro.framework.integration.HanekokoroApp
import io.github.shadowrz.projectkafka.libraries.androidutils.CustomTabsConnector

@ContributesTo(AppScope::class)
interface AppBindings {
    val customTabsConnector: CustomTabsConnector

    @ForScope(AppScope::class)
    val hanekokoroApp: HanekokoroApp
}
