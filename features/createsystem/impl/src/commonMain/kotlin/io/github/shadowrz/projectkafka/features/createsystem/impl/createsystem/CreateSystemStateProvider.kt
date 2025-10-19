package io.github.shadowrz.projectkafka.features.createsystem.impl.createsystem

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.ui.tooling.preview.PreviewParameterProvider

internal class CreateSystemStateProvider : PreviewParameterProvider<CreateSystemState> {
    override val values: Sequence<CreateSystemState>
        get() =
            sequenceOf(
                aCreateSystemState(),
                aCreateSystemState(initialText = "???? System"),
                aCreateSystemState(valid = false),
                aCreateSystemState(initialText = "???? System", valid = false),
            )
}

private fun aCreateSystemState(
    valid: Boolean = true,
    initialText: String = "",
) = CreateSystemState(
    valid = valid,
    textFieldState = TextFieldState(initialText = initialText),
)
