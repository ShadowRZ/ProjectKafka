package io.github.shadowrz.projectkafka.designsystem

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor

@Immutable
sealed class ButtonVariant {
    @Composable
    internal abstract fun shape(): Shape

    @Composable
    internal abstract fun colors(destructive: Boolean = false): ButtonColors

    @Composable
    internal open fun border(
        enabled: Boolean,
        destructive: Boolean = false,
    ): BorderStroke? = null

    @Composable
    internal abstract fun elevation(): ButtonElevation?

    internal open fun contentPadding(): PaddingValues = ButtonDefaults.ContentPadding

    data object Filled : ButtonVariant() {
        @Composable
        override fun shape(): Shape = ButtonDefaults.shape

        @Composable
        override fun colors(destructive: Boolean): ButtonColors =
            if (destructive) {
                ButtonDefaults.buttonColors(
                    containerColor = KafkaTheme.materialColors.error,
                    contentColor = KafkaTheme.materialColors.onError,
                    disabledContainerColor = KafkaTheme.materialColors.error.copy(alpha = 0.1f),
                    disabledContentColor = KafkaTheme.materialColors.onError.copy(alpha = 0.38f),
                )
            } else {
                ButtonDefaults.buttonColors()
            }

        @Composable
        override fun elevation(): ButtonElevation = ButtonDefaults.buttonElevation()
    }

    data object FilledTonal : ButtonVariant() {
        @Composable
        override fun shape(): Shape = ButtonDefaults.filledTonalShape

        @Composable
        override fun colors(destructive: Boolean): ButtonColors = ButtonDefaults.filledTonalButtonColors()

        @Composable
        override fun elevation(): ButtonElevation = ButtonDefaults.filledTonalButtonElevation()
    }

    data object Outlined : ButtonVariant() {
        @Composable
        override fun shape(): Shape = ButtonDefaults.outlinedShape

        @Composable
        override fun colors(destructive: Boolean): ButtonColors =
            if (destructive) {
                ButtonDefaults.outlinedButtonColors(
                    contentColor = KafkaTheme.materialColors.error,
                    disabledContentColor = KafkaTheme.materialColors.error.copy(alpha = 0.38f),
                )
            } else {
                ButtonDefaults.outlinedButtonColors()
            }

        @Composable
        override fun border(
            enabled: Boolean,
            destructive: Boolean,
        ): BorderStroke =
            ButtonDefaults
                .outlinedButtonBorder(
                    enabled,
                ).run {
                    val brush = this.brush
                    val alpha = if (enabled) 1f else 0.1f
                    copy(brush = if (destructive) SolidColor(KafkaTheme.materialColors.error.copy(alpha = alpha)) else brush)
                }

        @Composable
        override fun elevation(): ButtonElevation? = null
    }

    data object Text : ButtonVariant() {
        @Composable
        override fun shape(): Shape = ButtonDefaults.textShape

        @Composable
        override fun colors(destructive: Boolean): ButtonColors =
            if (destructive) {
                ButtonDefaults.textButtonColors(
                    contentColor = KafkaTheme.materialColors.error,
                    disabledContentColor = KafkaTheme.materialColors.error.copy(alpha = 0.38f),
                )
            } else {
                ButtonDefaults.textButtonColors()
            }

        override fun contentPadding(): PaddingValues = ButtonDefaults.TextButtonContentPadding

        @Composable
        override fun elevation(): ButtonElevation? = null
    }
}
