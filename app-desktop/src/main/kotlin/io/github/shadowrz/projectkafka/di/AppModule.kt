package io.github.shadowrz.projectkafka.di

import com.mikepenz.aboutlibraries.Libs
import com.mikepenz.aboutlibraries.util.withJson
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import io.github.shadowrz.projectkafka.BuildConfig
import io.github.shadowrz.projectkafka.buildmeta.BuildMeta
import io.github.shadowrz.projectkafka.libraries.di.annotations.ApplicationContext

@BindingContainer
@ContributesTo(AppScope::class)
object AppModule {
    @SingleIn(AppScope::class)
    @Provides
    fun providesLibs(): Libs =
        Libs
            .Builder()
            .withJson(
                requireNotNull(ClassLoader.getSystemResourceAsStream("aboutlibraries.json")) {
                    "AboutLibraries metadata was not generated!"
                }.readAllBytes(),
            ).build()

    @SingleIn(AppScope::class)
    @Provides
    fun providesBuildMeta(): BuildMeta =
        BuildMeta(
            applicationName = BuildConfig.APPLICATION_NAME,
            applicationId = BuildConfig.APPLICATION_ID,
            versionName = BuildConfig.VERSION_NAME,
            versionCode = BuildConfig.VERSION_CODE,
        )
}
