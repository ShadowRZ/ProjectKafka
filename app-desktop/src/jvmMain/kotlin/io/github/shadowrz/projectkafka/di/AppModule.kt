package io.github.shadowrz.projectkafka.di

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import io.github.shadowrz.projectkafka.buildmeta.BuildMeta

@BindingContainer
@ContributesTo(AppScope::class)
object AppModule {
    @SingleIn(AppScope::class)
    @Provides
    fun providesBuildMeta(): BuildMeta =
        BuildMeta(
            applicationName = "Project Kafka",
            applicationId = "io.github.shadowrz.projectkafka",
            versionName = "1.0",
            versionCode = 1,
        )
}
