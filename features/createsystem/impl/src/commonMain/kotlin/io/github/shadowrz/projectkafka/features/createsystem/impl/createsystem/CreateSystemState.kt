package io.github.shadowrz.projectkafka.features.createsystem.impl.createsystem

import androidx.compose.foundation.text.input.TextFieldState
import io.github.shadowrz.hanekokoro.framework.markers.HanekokoroState

data class CreateSystemState(
    val textFieldState: TextFieldState,
    val valid: Boolean,
) : HanekokoroState
