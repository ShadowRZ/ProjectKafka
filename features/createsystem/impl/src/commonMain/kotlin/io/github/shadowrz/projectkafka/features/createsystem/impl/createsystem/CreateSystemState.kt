package io.github.shadowrz.projectkafka.features.createsystem.impl.createsystem

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Stable

@Stable
data class CreateSystemState(
    val textFieldState: TextFieldState,
    val valid: Boolean,
)
