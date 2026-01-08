package io.github.shadowrz.projectkafka.libraries.preferences.impl

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import dev.dirs.ProjectDirectories
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import dev.zacsweers.metro.binding
import io.github.shadowrz.projectkafka.libraries.di.annotations.IOScope
import io.github.shadowrz.projectkafka.libraries.preferences.api.AppPreferencesStore
import kotlinx.coroutines.CoroutineScope
import okio.Path.Companion.toPath

@SingleIn(AppScope::class)
@ContributesBinding(
    AppScope::class,
    binding = binding<AppPreferencesStore>(),
)
@Inject
class JavaAppPreferencesStore(
    projectDirectories: ProjectDirectories,
    @IOScope private val scope: CoroutineScope,
) : LocalAppPreferencesStore() {
    override val store = PreferenceDataStoreFactory.createWithPath(scope = scope) {
        projectDirectories.preferenceDir.toPath(normalize = true) / "projectkafka.preferences_pb"
    }
}
