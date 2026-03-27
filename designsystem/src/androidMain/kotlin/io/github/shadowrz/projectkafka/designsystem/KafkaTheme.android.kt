package io.github.shadowrz.projectkafka.designsystem

import android.os.Build
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import io.github.shadowrz.projectkafka.designsystem.colors.darkScheme
import io.github.shadowrz.projectkafka.designsystem.colors.lightScheme
import io.github.shadowrz.projectkafka.designsystem.internal.Typography
import io.github.shadowrz.projectkafka.designsystem.internal.baseline

@Composable
actual fun KafkaTheme(
    darkTheme: Boolean,
    dynamicColor: Boolean,
    useSystemFont: Boolean,
    content: @Composable (() -> Unit),
) {
    val colorScheme =
        when {
            dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
                val context = LocalContext.current
                if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
            }

            darkTheme -> {
                darkScheme
            }

            else -> {
                lightScheme
            }
        }
    val typography = if (useSystemFont) baseline else Typography

    MaterialTheme(
        colorScheme = colorScheme,
        typography = typography,
        content = content,
    )
}
