package io.github.shadowrz.projectkafka.features.preferences.impl.root

sealed interface PreferencesRootEvents {
    data class ChangeAllowsMultiSystem(
        val allowsMultiSystem: Boolean,
    ) : PreferencesRootEvents

    data class ChangeUseSystemFont(
        val useSystemFont: Boolean,
    ) : PreferencesRootEvents
}
