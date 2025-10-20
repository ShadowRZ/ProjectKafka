package io.github.shadowrz.projectkafka.di

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import io.github.shadowrz.projectkafka.libraries.androidutils.CustomTabsConnector

@ContributesTo(AppScope::class)
interface AppBindings {
    val customTabsConnector: CustomTabsConnector
}
