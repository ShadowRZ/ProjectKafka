package io.github.shadowrz.projectkafka.libraries.preferences.impl

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import dev.zacsweers.metro.binding
import io.github.shadowrz.projectkafka.libraries.data.api.System
import io.github.shadowrz.projectkafka.libraries.di.SystemScope
import io.github.shadowrz.projectkafka.libraries.di.annotations.ApplicationContext
import io.github.shadowrz.projectkafka.libraries.di.annotations.IOScope
import io.github.shadowrz.projectkafka.libraries.preferences.api.SystemPreferencesStore
import kotlinx.coroutines.CoroutineScope

@SingleIn(SystemScope::class)
@ContributesBinding(
    SystemScope::class,
    binding = binding<SystemPreferencesStore>(),
)
@Inject
class AndroidSystemPreferencesStore(
    @ApplicationContext context: Context,
    @IOScope.SystemScoped private val scope: CoroutineScope,
    system: System,
) : LocalSystemPreferencesStore() {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("projectkafka-${system.id}", scope = scope)

    override val store: DataStore<Preferences> = context.dataStore
}
