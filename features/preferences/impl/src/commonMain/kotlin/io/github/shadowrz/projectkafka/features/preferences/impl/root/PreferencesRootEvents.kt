package io.github.shadowrz.projectkafka.features.preferences.impl.root

sealed interface PreferencesRootEvents {
    data class ChangeAllowsMultiSystem(
        val allowsMultiSystem: Boolean,
    ) : PreferencesRootEvents
}
