package io.github.shadowrz.projectkafka.di

import dev.dirs.ProjectDirectories
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import io.github.shadowrz.projectkafka.libraries.di.annotations.CacheDirectory
import io.github.shadowrz.projectkafka.libraries.di.annotations.FilesDirectory
import okio.Path
import okio.Path.Companion.toPath

@BindingContainer
@ContributesTo(AppScope::class)
object FilesystemModule {
    @Provides
    private fun providesProjectDirectories(): ProjectDirectories = ProjectDirectories.from("io.github", "ShadowRZ", "ProjectKafka")

    @Provides
    @CacheDirectory
    fun providesCacheDirectory(dirs: ProjectDirectories): Path = dirs.cacheDir.toPath(normalize = true)

    @Provides
    @FilesDirectory
    fun providesFilesDirectory(dirs: ProjectDirectories): Path = dirs.dataDir.toPath(normalize = true)
}
