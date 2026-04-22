package io.github.shadowrz.projectkafka.designsystem

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import io.github.shadowrz.projectkafka.designsystem.icons.Check

@Composable
fun FilterChip(
    selected: Boolean,
    onClick: () -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    interactionSource: MutableInteractionSource? = null,
) {
    androidx.compose.material3.FilterChip(
        modifier = modifier,
        selected = selected,
        onClick = onClick,
        label = { Text(label) },
        enabled = enabled,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        interactionSource = interactionSource,
    )
}

@Composable
fun FilterChip(
    selected: Boolean,
    onClick: () -> Unit,
    label: String,
    leadingIcon: ImageVector,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource? = null,
) {
    androidx.compose.material3.FilterChip(
        modifier = modifier,
        selected = selected,
        onClick = onClick,
        label = { Text(label) },
        enabled = enabled,
        leadingIcon = {
            Icon(
                if (selected) KafkaIcons.Check else leadingIcon,
                contentDescription = null,
                modifier = Modifier.size(FilterChipDefaults.IconSize),
            )
        },
        interactionSource = interactionSource,
    )
}

object FilterChipDefaults {
    val IconSize: Dp = FilterChipDefaults.IconSize
}
