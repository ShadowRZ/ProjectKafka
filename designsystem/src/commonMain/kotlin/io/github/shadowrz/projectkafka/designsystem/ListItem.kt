package io.github.shadowrz.projectkafka.designsystem

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.ListItemColors
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import io.github.shadowrz.projectkafka.designsystem.modifier.maybeClickable

@Composable
@NonRestartableComposable
fun ListItem(
    headlineContent: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    supportingContent: @Composable (() -> Unit)? = null,
    leadingContent: @Composable (() -> Unit)? = null,
    trailingContent: @Composable (() -> Unit)? = null,
    overlineContent: @Composable (() -> Unit)? = null,
    style: ListItemStyle = ListItemStyle.Default,
    enabled: Boolean = true,
    onClick: (() -> Unit)? = null,
) {
    ListItem(
        modifier = modifier,
        headlineContent = headlineContent,
        supportingContent = supportingContent,
        leadingContent = leadingContent,
        trailingContent = trailingContent,
        overlineContent = overlineContent,
        enabled = enabled,
        onClick = onClick,
        colors = ListItemDefaults.colors(
            containerColor = Color.Transparent,
            headlineColor = style.headlineColor(),
            supportingColor = style.supportingColor(),
            leadingIconColor = style.leadingIconColor(),
            trailingIconColor = style.trailingIconColor(),
            overlineColor = style.overlineColor(),
        ),
    )
}

@Composable
fun ListItem(
    headlineContent: @Composable () -> Unit,
    colors: ListItemColors,
    modifier: Modifier = Modifier,
    supportingContent: @Composable (() -> Unit)? = null,
    leadingContent: @Composable (() -> Unit)? = null,
    trailingContent: @Composable (() -> Unit)? = null,
    overlineContent: @Composable (() -> Unit)? = null,
    enabled: Boolean = true,
    onClick: (() -> Unit)? = null,
) {
    val headlineColor = if (enabled) colors.headlineColor else colors.disabledHeadlineColor
    val supportingTextColor = if (enabled) colors.supportingTextColor else colors.supportingTextColor.copy(alpha = 0.38f)
    val leadingIconColor = if (enabled) colors.leadingIconColor else colors.disabledLeadingIconColor
    val trailingIconColor = if (enabled) colors.trailingIconColor else colors.disabledTrailingIconColor
    val overlineColor = if (enabled) colors.overlineColor else colors.overlineColor.copy(alpha = 0.38f)

    val decoratedSupportingContent: (@Composable () -> Unit)? = supportingContent?.let { content ->
        {
            CompositionLocalProvider(
                LocalContentColor provides supportingTextColor,
            ) {
                content()
            }
        }
    }
    val decoratedLeadingContent: (@Composable () -> Unit)? = leadingContent?.let { content ->
        {
            CompositionLocalProvider(
                LocalContentColor provides leadingIconColor,
            ) {
                content()
            }
        }
    }
    val decoratedTraillingContent: (@Composable () -> Unit)? = trailingContent?.let { content ->
        {
            CompositionLocalProvider(
                LocalContentColor provides trailingIconColor,
            ) {
                content()
            }
        }
    }
    val decoratedOverlineContent: (@Composable () -> Unit)? = overlineContent?.let { content ->
        {
            CompositionLocalProvider(
                LocalContentColor provides overlineColor,
            ) {
                content()
            }
        }
    }

    androidx.compose.material3.ListItem(
        modifier = modifier.maybeClickable(onClick),
        headlineContent = {
            CompositionLocalProvider(
                LocalContentColor provides headlineColor,
                content = headlineContent,
            )
        },
        supportingContent = decoratedSupportingContent,
        leadingContent = decoratedLeadingContent,
        trailingContent = decoratedTraillingContent,
        overlineContent = decoratedOverlineContent,
        colors = colors,
    )
}

@Composable
fun RadioButtonListItem(
    selected: Boolean,
    headlineContent: @Composable () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    supportingContent: @Composable (() -> Unit)? = null,
    enabled: Boolean = true,
) {
    val interactionSource = remember { MutableInteractionSource() }

    ListItem(
        modifier = modifier.selectable(
            selected = selected,
            enabled = enabled,
            interactionSource = interactionSource,
            indication = LocalIndication.current,
            onClick = onClick,
        ),
        enabled = enabled,
        headlineContent = headlineContent,
        supportingContent = supportingContent,
        leadingContent = {
            RadioButton(
                selected = selected,
                enabled = enabled,
                onClick = onClick,
            )
        },
    )
}

@Composable
fun CheckboxListItem(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    headlineContent: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    supportingContent: @Composable (() -> Unit)? = null,
    enabled: Boolean = true,
) {
    val interactionSource = remember { MutableInteractionSource() }

    ListItem(
        modifier = modifier.toggleable(
            value = checked,
            enabled = enabled,
            interactionSource = interactionSource,
            indication = LocalIndication.current,
            onValueChange = onCheckedChange,
        ),
        enabled = enabled,
        headlineContent = headlineContent,
        supportingContent = supportingContent,
        leadingContent = {
            Checkbox(
                checked = checked,
                enabled = enabled,
                onCheckedChange = onCheckedChange,
                interactionSource = interactionSource,
            )
        },
    )
}

@Immutable
sealed interface ListItemStyle {
    data object Default : ListItemStyle

    data object Primary : ListItemStyle

    data object Destructive : ListItemStyle

    @Composable
    fun headlineColor(): Color =
        when (this) {
            Default -> Color.Unspecified
            Destructive -> KafkaTheme.materialColors.error
            Primary -> KafkaTheme.materialColors.primary
        }

    @Composable
    fun supportingColor(): Color =
        when (this) {
            Default -> Color.Unspecified
            Destructive -> KafkaTheme.materialColors.error
            Primary -> KafkaTheme.materialColors.primary
        }

    @Composable
    fun leadingIconColor(): Color =
        when (this) {
            Default, Primary -> Color.Unspecified
            Destructive -> KafkaTheme.materialColors.error
        }

    @Composable
    fun trailingIconColor(): Color =
        when (this) {
            Default, Primary -> Color.Unspecified
            Destructive -> KafkaTheme.materialColors.error
        }

    @Composable
    fun overlineColor(): Color =
        when (this) {
            Default -> Color.Unspecified
            Destructive -> KafkaTheme.materialColors.error
            Primary -> KafkaTheme.materialColors.primary
        }
}
