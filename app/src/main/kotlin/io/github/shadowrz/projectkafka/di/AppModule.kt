package io.github.shadowrz.projectkafka.di

import android.content.Context
import com.mikepenz.aboutlibraries.Libs
import com.mikepenz.aboutlibraries.util.withJson
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.ForScope
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import io.github.shadowrz.hanekokoro.framework.integration.HanekokoroApp
import io.github.shadowrz.hanekokoro.framework.runtime.component.Component
import io.github.shadowrz.hanekokoro.framework.runtime.coroutines.supervisorScope
import io.github.shadowrz.hanekokoro.framework.runtime.renderer.Renderer
import io.github.shadowrz.projectkafka.BuildConfig
import io.github.shadowrz.projectkafka.R
import io.github.shadowrz.projectkafka.buildmeta.BuildMeta
import io.github.shadowrz.projectkafka.libraries.androidutils.CustomTabsConnector
import io.github.shadowrz.projectkafka.libraries.androidutils.LocaleConfigCompat
import io.github.shadowrz.projectkafka.libraries.core.coroutine.CoroutineDispatchers
import io.github.shadowrz.projectkafka.libraries.di.annotations.ApplicationContext
import io.github.shadowrz.projectkafka.libraries.di.annotations.IOScope
import io.github.shadowrz.projectkafka.libraries.strings.CommonStrings
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.plus
import kotlin.reflect.KClass

@BindingContainer
@ContributesTo(AppScope::class)
object AppModule {
    @SingleIn(AppScope::class)
    @Provides
    fun providesCoroutineDispatchers(): CoroutineDispatchers = CoroutineDispatchers.Default

    @SingleIn(AppScope::class)
    @Provides
    fun providesAppCoroutineScope(): CoroutineScope = MainScope() + CoroutineName("ProjectKafka")

    @IOScope
    @SingleIn(AppScope::class)
    @Provides
    fun providesAppIOCoroutineScope(
        dispatchers: CoroutineDispatchers,
        appCoroutineScope: CoroutineScope,
    ): CoroutineScope =
        appCoroutineScope.supervisorScope(
            context = dispatchers.io + CoroutineName("ProjectKafka.IOScope"),
        )

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
            applicationName = context.getString(CommonStrings.app_name),
            applicationId = BuildConfig.APPLICATION_ID,
            versionName = BuildConfig.VERSION_NAME,
            versionCode = BuildConfig.VERSION_CODE,
        )

    @SingleIn(AppScope::class)
    @ForScope(AppScope::class)
    @Provides
    fun providesHanekokoroApp(
        componentFactories: Map<KClass<out Component>, Component.Factory<*>>,
        renderers: Map<KClass<out Component>, Renderer<*>>,
    ): HanekokoroApp =
        HanekokoroApp
            .Builder()
            .addComponentFactories(componentFactories)
            .addRenderers(renderers)
            .build()
}
