package io.github.shadowrz.projectkafka.designsystem

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import io.github.shadowrz.projectkafka.designsystem.internal.useVariableFonts
import org.jetbrains.compose.resources.Font
import projectkafka.designsystem.generated.resources.Res
import projectkafka.designsystem.generated.resources.space_grotesk_bold
import projectkafka.designsystem.generated.resources.space_grotesk_light
import projectkafka.designsystem.generated.resources.space_grotesk_medium
import projectkafka.designsystem.generated.resources.space_grotesk_regular
import projectkafka.designsystem.generated.resources.space_grotesk_variable

/**
 * A subset of Material 3 [Typography] color tokens, along with custom tokens.
 *
 * @see androidx.compose.material3.Typography
 */
@Immutable
data class KafkaTypography(
    val displayLarge: TextStyle,
    val displayMedium: TextStyle,
    val displaySmall: TextStyle,
    val headlineLarge: TextStyle,
    val headlineMedium: TextStyle,
    val headlineSmall: TextStyle,
    val titleLarge: TextStyle,
    val titleMedium: TextStyle,
    val titleSmall: TextStyle,
    val bodyLarge: TextStyle,
    val bodyMedium: TextStyle,
    val bodySmall: TextStyle,
    val labelLarge: TextStyle,
    val labelMedium: TextStyle,
    val labelSmall: TextStyle,
) {
    internal constructor(typography: Typography) : this(
        displayLarge = typography.displayLarge,
        displayMedium = typography.displayMedium,
        displaySmall = typography.displaySmall,
        headlineLarge = typography.headlineLarge,
        headlineMedium = typography.headlineMedium,
        headlineSmall = typography.headlineSmall,
        titleLarge = typography.titleLarge,
        titleMedium = typography.titleMedium,
        titleSmall = typography.titleSmall,
        bodyLarge = typography.bodyLarge,
        bodyMedium = typography.bodyMedium,
        bodySmall = typography.bodySmall,
        labelLarge = typography.labelLarge,
        labelMedium = typography.labelMedium,
        labelSmall = typography.labelSmall,
    )

    internal companion object {
        var cachedFontFamily: FontFamily? = null

        val FontFamily
            @Composable
            get() = cachedFontFamily ?: if (useVariableFonts()) {
                FontFamily(
                    Font(
                        Res.font.space_grotesk_variable,
                        FontWeight.Light,
                        variationSettings =
                            FontVariation.Settings(
                                FontVariation.weight(FontWeight.Light.weight),
                            ),
                    ),
                    Font(
                        Res.font.space_grotesk_variable,
                        FontWeight.Normal,
                        variationSettings =
                            FontVariation.Settings(
                                FontVariation.weight(FontWeight.Normal.weight),
                            ),
                    ),
                    Font(
                        Res.font.space_grotesk_variable,
                        FontWeight.Medium,
                        variationSettings =
                            FontVariation.Settings(
                                FontVariation.weight(FontWeight.Medium.weight),
                            ),
                    ),
                    Font(
                        Res.font.space_grotesk_variable,
                        FontWeight.Bold,
                        variationSettings =
                            FontVariation.Settings(
                                FontVariation.weight(FontWeight.Bold.weight),
                            ),
                    ),
                )
            } else {
                FontFamily(
                    Font(Res.font.space_grotesk_light, FontWeight.Light),
                    Font(Res.font.space_grotesk_regular, FontWeight.Normal),
                    Font(Res.font.space_grotesk_medium, FontWeight.Medium),
                    Font(Res.font.space_grotesk_bold, FontWeight.Bold),
                )
            }.also { cachedFontFamily = it }
    }
}
