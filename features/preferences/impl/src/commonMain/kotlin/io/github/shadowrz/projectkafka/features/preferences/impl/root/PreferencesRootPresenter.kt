package io.github.shadowrz.projectkafka.features.preferences.impl.root

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import io.github.shadowrz.hanekokoro.framework.runtime.Presenter
import io.github.shadowrz.projectkafka.libraries.preferences.api.AppPreferencesStore
import kotlinx.coroutines.launch

@Inject
@ContributesBinding(AppScope::class)
class PreferencesRootPresenter(
    private val appPreferencesStore: AppPreferencesStore,
) : Presenter<PreferencesRootState> {
    @Composable
    override fun present(): PreferencesRootState {
        val scope = rememberCoroutineScope()
        val allowsMultiSystem by appPreferencesStore.allowsMultiSystem().collectAsState(false)

        return PreferencesRootState(
            allowsMultiSystem = allowsMultiSystem,
        ) {
            when (it) {
                is PreferencesRootEvents.ChangeAllowsMultiSystem -> scope.launch {
                    appPreferencesStore.setAllowsMultiSystem(it.allowsMultiSystem)
                }
            }
        }
    }
}
