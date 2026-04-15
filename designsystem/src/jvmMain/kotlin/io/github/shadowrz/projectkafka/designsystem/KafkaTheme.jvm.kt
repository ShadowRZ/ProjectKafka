package io.github.shadowrz.projectkafka.designsystem

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import io.github.shadowrz.projectkafka.designsystem.colors.darkScheme
import io.github.shadowrz.projectkafka.designsystem.colors.lightScheme

@Composable
internal actual fun materialColorScheme(
    darkTheme: Boolean,
    dynamicColor: Boolean,
): ColorScheme = if (darkTheme) darkScheme else lightScheme
