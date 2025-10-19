package io.github.shadowrz.projectkafka.libraries.components.theme

import android.os.Build
import androidx.compose.material3.Typography
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import io.github.shadowrz.projectkafka.assets.SharedFonts

@OptIn(ExperimentalTextApi::class)
val fontFamily =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        FontFamily(
            Font(
                SharedFonts.space_grotesk_variable,
                FontWeight.Light,
                variationSettings =
                    FontVariation.Settings(
                        FontVariation.weight(FontWeight.Light.weight),
                    ),
            ),
            Font(
                SharedFonts.space_grotesk_variable,
                FontWeight.Normal,
                variationSettings =
                    FontVariation.Settings(
                        FontVariation.weight(FontWeight.Normal.weight),
                    ),
            ),
            Font(
                SharedFonts.space_grotesk_variable,
                FontWeight.Medium,
                variationSettings =
                    FontVariation.Settings(
                        FontVariation.weight(FontWeight.Medium.weight),
                    ),
            ),
            Font(
                SharedFonts.space_grotesk_variable,
                FontWeight.Bold,
                variationSettings =
                    FontVariation.Settings(
                        FontVariation.weight(FontWeight.Bold.weight),
                    ),
            ),
        )
    } else {
        FontFamily(
            Font(SharedFonts.space_grotesk_light, FontWeight.Light),
            Font(SharedFonts.space_grotesk_regular, FontWeight.Normal),
            Font(SharedFonts.space_grotesk_medium, FontWeight.Medium),
            Font(SharedFonts.space_grotesk_bold, FontWeight.Bold),
        )
    }

actual val Typography: Typography =
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
