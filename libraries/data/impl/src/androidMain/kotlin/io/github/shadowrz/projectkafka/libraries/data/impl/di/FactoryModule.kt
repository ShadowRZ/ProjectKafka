package io.github.shadowrz.projectkafka.libraries.data.impl.di

import androidx.sqlite.db.SupportSQLiteOpenHelper
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn

@BindingContainer
@ContributesTo(AppScope::class)
object FactoryModule {
    @Provides
    @SingleIn(AppScope::class)
    fun providesSupportSQLiteOpenHelperFactory(): SupportSQLiteOpenHelper.Factory = FrameworkSQLiteOpenHelperFactory()
}
