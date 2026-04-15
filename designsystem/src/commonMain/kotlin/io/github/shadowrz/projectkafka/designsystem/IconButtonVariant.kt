package io.github.shadowrz.projectkafka.designsystem

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor

@Immutable
sealed class IconButtonVariant {
    @Composable
    internal abstract fun shape(): Shape

    @Composable
    internal abstract fun colors(destructive: Boolean = false): IconButtonColors

    data object Default : IconButtonVariant() {
        @Composable
        override fun shape(): Shape = ButtonDefaults.shape

        @Composable
        override fun colors(destructive: Boolean): IconButtonColors =
            if (destructive) {
                IconButtonDefaults.iconButtonColors(
                    contentColor = KafkaTheme.materialColors.error,
                    disabledContentColor = KafkaTheme.materialColors.error.copy(alpha = 0.38f),
                )
            } else {
                IconButtonDefaults.iconButtonColors()
            }
    }

    data object Filled : IconButtonVariant() {
        @Composable
        override fun shape(): Shape = ButtonDefaults.shape

        @Composable
        override fun colors(destructive: Boolean): IconButtonColors =
            if (destructive) {
                IconButtonDefaults.filledIconButtonColors(
                    containerColor = KafkaTheme.materialColors.error,
                    contentColor = KafkaTheme.materialColors.onError,
                    disabledContainerColor = KafkaTheme.materialColors.error.copy(alpha = 0.1f),
                    disabledContentColor = KafkaTheme.materialColors.onError.copy(alpha = 0.38f),
                )
            } else {
                IconButtonDefaults.filledIconButtonColors()
            }
    }

    data object FilledTonal : IconButtonVariant() {
        @Composable
        override fun shape(): Shape = ButtonDefaults.filledTonalShape

        @Composable
        override fun colors(destructive: Boolean): IconButtonColors = IconButtonDefaults.filledTonalIconButtonColors()
    }
}
