package io.github.shadowrz.projectkafka.designsystem

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Modifier

@Composable
@NonRestartableComposable
fun IconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource? = null,
    destructive: Boolean = false,
    variant: IconButtonVariant = IconButtonVariant.Default,
    content: @Composable () -> Unit,
) = androidx.compose.material3.IconButton(
    modifier = modifier,
    onClick = onClick,
    enabled = enabled,
    interactionSource = interactionSource,
    content = content,
    shape = variant.shape(),
    colors = variant.colors(destructive = destructive),
)
