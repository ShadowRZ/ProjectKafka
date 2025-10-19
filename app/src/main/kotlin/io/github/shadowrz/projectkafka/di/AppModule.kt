package io.github.shadowrz.projectkafka.di

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.mikepenz.aboutlibraries.Libs
import com.mikepenz.aboutlibraries.util.withJson
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import io.github.shadowrz.projectkafka.R
import io.github.shadowrz.projectkafka.libraries.androidutils.LocaleConfigCompat
import io.github.shadowrz.projectkafka.libraries.core.coroutine.CoroutineDispatchers
import io.github.shadowrz.projectkafka.libraries.di.annotations.ApplicationContext
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.plus

@BindingContainer
@ContributesTo(AppScope::class)
object AppModule {
    @SingleIn(AppScope::class)
    @Provides
    fun providesCoroutineDispatchers(): CoroutineDispatchers =
        CoroutineDispatchers(
            io = Dispatchers.IO,
            computation = Dispatchers.Default,
            main = Dispatchers.Main,
        )

    @SingleIn(AppScope::class)
    @Provides
    fun providesAppCoroutineScope(): CoroutineScope = MainScope() + CoroutineName("ProjectKafka")

    @SingleIn(AppScope::class)
    @Provides
    fun providesSharedPreferences(
        @ApplicationContext context: Context,
    ): SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

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
}
