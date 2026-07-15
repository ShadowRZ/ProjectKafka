package io.github.shadowrz.projectkafka.designsystem

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/**
 * A subset of Material 3 [ColorScheme] color tokens.
 *
 * @see androidx.compose.material3.ColorScheme
 */
@Immutable
data class KafkaColors(
    val primary: Color,
    val onPrimary: Color,
    val primaryContainer: Color,
    val onPrimaryContainer: Color,
    val secondary: Color,
    val secondaryContainer: Color,
    val onSecondaryContainer: Color,
    val tertiary: Color,
    val tertiaryContainer: Color,
    val onTertiaryContainer: Color,
    val background: Color,
    val onBackground: Color,
    val surface: Color,
    val onSurface: Color,
    val surfaceVariant: Color,
    val onSurfaceVariant: Color,
    val inverseOnSurface: Color,
    val error: Color,
    val onError: Color,
    val surfaceContainer: Color,
) {
    /**
     * Derive [KafkaColors] from a Material 3 [ColorScheme].
     *
     * @param colorScheme Material 3 [ColorScheme].
     */
    internal constructor(
        colorScheme: ColorScheme
    ) : this(
        primary = colorScheme.primary,
        onPrimary = colorScheme.onPrimary,
        primaryContainer = colorScheme.primaryContainer,
        onPrimaryContainer = colorScheme.onPrimaryContainer,
        secondary = colorScheme.secondary,
        secondaryContainer = colorScheme.secondaryContainer,
        onSecondaryContainer = colorScheme.onSecondaryContainer,
        tertiary = colorScheme.tertiary,
        tertiaryContainer = colorScheme.tertiaryContainer,
        onTertiaryContainer = colorScheme.onTertiaryContainer,
        background = colorScheme.background,
        onBackground = colorScheme.onBackground,
        surface = colorScheme.surface,
        onSurface = colorScheme.onSurface,
        surfaceVariant = colorScheme.surfaceVariant,
        onSurfaceVariant = colorScheme.onSurfaceVariant,
        inverseOnSurface = colorScheme.inverseOnSurface,
        error = colorScheme.error,
        onError = colorScheme.onError,
        surfaceContainer = colorScheme.surfaceContainer,
    )
}
