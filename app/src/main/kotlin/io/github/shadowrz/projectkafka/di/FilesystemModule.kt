package io.github.shadowrz.projectkafka.di

import android.content.Context
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import io.github.shadowrz.projectkafka.libraries.di.annotations.ApplicationContext
import io.github.shadowrz.projectkafka.libraries.di.annotations.CacheDirectory
import io.github.shadowrz.projectkafka.libraries.di.annotations.FilesDirectory
import okio.FileSystem
import okio.Path
import okio.Path.Companion.toOkioPath

@BindingContainer
@ContributesTo(AppScope::class)
object FilesystemModule {
    @Provides
    @CacheDirectory
    fun providesCacheDirectory(
        @ApplicationContext context: Context,
    ): Path = context.cacheDir.toOkioPath(normalize = true)

    @Provides
    @FilesDirectory
    fun providesFilesDirectory(
        @ApplicationContext context: Context,
    ): Path = context.filesDir.toOkioPath(normalize = true)

    @SingleIn(AppScope::class)
    @Provides
    fun providesFileSystem(): FileSystem = FileSystem.SYSTEM
}
