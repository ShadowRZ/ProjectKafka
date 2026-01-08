package io.github.shadowrz.projectkafka.libraries.preferences.impl

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import dev.dirs.ProjectDirectories
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import dev.zacsweers.metro.binding
import io.github.shadowrz.projectkafka.libraries.data.api.System
import io.github.shadowrz.projectkafka.libraries.di.SystemScope
import io.github.shadowrz.projectkafka.libraries.di.annotations.IOScope
import io.github.shadowrz.projectkafka.libraries.preferences.api.SystemPreferencesStore
import kotlinx.coroutines.CoroutineScope
import okio.Path.Companion.toPath

@SingleIn(SystemScope::class)
@ContributesBinding(
    SystemScope::class,
    binding = binding<SystemPreferencesStore>(),
)
@Inject
class JavaSystemPreferencesStore(
    projectDirectories: ProjectDirectories,
    @IOScope.SystemScoped private val scope: CoroutineScope,
    system: System,
) : LocalSystemPreferencesStore() {
    override val store = PreferenceDataStoreFactory.createWithPath(scope = scope) {
        projectDirectories.preferenceDir.toPath(normalize = true) / "projectkafka-${system.id}.preferences_pb"
    }
}
