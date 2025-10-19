package io.github.shadowrz.projectkafka.libraries.data.impl.di

import androidx.sqlite.db.SupportSQLiteOpenHelper
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import io.requery.android.database.sqlite.RequerySQLiteOpenHelperFactory

@BindingContainer
@ContributesTo(AppScope::class)
object FactoryModule {
    @Provides
    @SingleIn(AppScope::class)
    fun providesSupportSQLiteOpenHelperFactory(): SupportSQLiteOpenHelper.Factory = RequerySQLiteOpenHelperFactory()
}
