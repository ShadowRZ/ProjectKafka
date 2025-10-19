package io.github.shadowrz.projectkafka.libraries.preferences.impl

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import dev.zacsweers.metro.binding
import io.github.shadowrz.projectkafka.libraries.di.annotations.ApplicationContext
import io.github.shadowrz.projectkafka.libraries.preferences.api.AppPreferencesStore

@SingleIn(AppScope::class)
@ContributesBinding(
    AppScope::class,
    binding = binding<AppPreferencesStore>(),
)
@Inject
class AndroidAppPreferencesStore(
    @ApplicationContext context: Context,
) : LocalAppPreferencesStore() {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        "projectkafka",
    )

    override val store = context.dataStore
}
