package io.github.shadowrz.projectkafka.di

import android.content.Context
import com.mikepenz.aboutlibraries.Libs
import com.mikepenz.aboutlibraries.util.withJson
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import io.github.shadowrz.projectkafka.BuildConfig
import io.github.shadowrz.projectkafka.R
import io.github.shadowrz.projectkafka.buildmeta.BuildMeta
import io.github.shadowrz.projectkafka.libraries.androidutils.CustomTabsConnector
import io.github.shadowrz.projectkafka.libraries.androidutils.LocaleConfigCompat
import io.github.shadowrz.projectkafka.libraries.di.annotations.ApplicationContext

@BindingContainer
@ContributesTo(AppScope::class)
object AppModule {
    @SingleIn(AppScope::class)
    @Provides
    fun providesLocaleConfigCompat(
        @ApplicationContext context: Context,
    ): LocaleConfigCompat = LocaleConfigCompat(context = context)

    @SingleIn(AppScope::class)
    @Provides
    fun providesLibs(
        @ApplicationContext context: Context,
    ): Libs = Libs.Builder().withJson(context, R.raw.aboutlibraries).build()

    @SingleIn(AppScope::class)
    @Provides
    fun providesCustomTabsConnector(
        @ApplicationContext context: Context,
    ): CustomTabsConnector = CustomTabsConnector(context)

    @SingleIn(AppScope::class)
    @Provides
    fun providesBuildMeta(
        @ApplicationContext context: Context,
    ): BuildMeta =
        BuildMeta(
            applicationName = context.getString(R.string.app_name),
            applicationId = BuildConfig.APPLICATION_ID,
            versionName = BuildConfig.VERSION_NAME,
            versionCode = BuildConfig.VERSION_CODE,
        )
}
