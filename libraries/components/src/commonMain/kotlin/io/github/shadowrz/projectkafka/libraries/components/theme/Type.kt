package io.github.shadowrz.projectkafka.libraries.components.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.Font
import projectkafka.libraries.components.generated.resources.Res
import projectkafka.libraries.components.generated.resources.space_grotesk_bold
import projectkafka.libraries.components.generated.resources.space_grotesk_light
import projectkafka.libraries.components.generated.resources.space_grotesk_medium
import projectkafka.libraries.components.generated.resources.space_grotesk_regular
import projectkafka.libraries.components.generated.resources.space_grotesk_variable

internal expect fun useVariableFonts(): Boolean

// Set of Material typography styles to start with
val baseline = Typography()

@get:Composable
@OptIn(ExperimentalTextApi::class)
val fontFamily get() =
    if (useVariableFonts()) {
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
    }

@get:Composable
val Typography: Typography get() =
    Typography(
        displayLarge = baseline.displayLarge.copy(fontFamily = fontFamily),
        displayMedium = baseline.displayMedium.copy(fontFamily = fontFamily),
        displaySmall = baseline.displaySmall.copy(fontFamily = fontFamily),
        headlineLarge = baseline.headlineLarge.copy(fontFamily = fontFamily),
        headlineMedium = baseline.headlineMedium.copy(fontFamily = fontFamily),
        headlineSmall = baseline.headlineSmall.copy(fontFamily = fontFamily),
        titleLarge = baseline.titleLarge.copy(fontFamily = fontFamily),
        titleMedium = baseline.titleMedium.copy(fontFamily = fontFamily),
        titleSmall = baseline.titleSmall.copy(fontFamily = fontFamily),
        bodyLarge = baseline.bodyLarge.copy(fontFamily = fontFamily),
        bodyMedium = baseline.bodyMedium.copy(fontFamily = fontFamily),
        bodySmall = baseline.bodySmall.copy(fontFamily = fontFamily),
        labelLarge = baseline.labelLarge.copy(fontFamily = fontFamily),
        labelMedium = baseline.labelMedium.copy(fontFamily = fontFamily),
        labelSmall = baseline.labelSmall.copy(fontFamily = fontFamily),
    )

val SystemTypography: Typography get() =
    Typography(
        displayLarge = baseline.displayLarge.copy(fontFamily = FontFamily.Default),
        displayMedium = baseline.displayMedium.copy(fontFamily = FontFamily.Default),
        displaySmall = baseline.displaySmall.copy(fontFamily = FontFamily.Default),
        headlineLarge = baseline.headlineLarge.copy(fontFamily = FontFamily.Default),
        headlineMedium = baseline.headlineMedium.copy(fontFamily = FontFamily.Default),
        headlineSmall = baseline.headlineSmall.copy(fontFamily = FontFamily.Default),
        titleLarge = baseline.titleLarge.copy(fontFamily = FontFamily.Default),
        titleMedium = baseline.titleMedium.copy(fontFamily = FontFamily.Default),
        titleSmall = baseline.titleSmall.copy(fontFamily = FontFamily.Default),
        bodyLarge = baseline.bodyLarge.copy(fontFamily = FontFamily.Default),
        bodyMedium = baseline.bodyMedium.copy(fontFamily = FontFamily.Default),
        bodySmall = baseline.bodySmall.copy(fontFamily = FontFamily.Default),
        labelLarge = baseline.labelLarge.copy(fontFamily = FontFamily.Default),
        labelMedium = baseline.labelMedium.copy(fontFamily = FontFamily.Default),
        labelSmall = baseline.labelSmall.copy(fontFamily = FontFamily.Default),
    )
