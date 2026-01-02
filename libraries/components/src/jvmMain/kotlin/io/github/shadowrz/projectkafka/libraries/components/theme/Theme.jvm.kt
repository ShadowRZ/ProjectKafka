package io.github.shadowrz.projectkafka.libraries.components.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Suppress("unused")
@Composable
actual fun ProjectKafkaTheme(
    darkTheme: Boolean,
    dynamicColor: Boolean,
    content: @Composable (() -> Unit),
) {
    val colorScheme =
        when {
            darkTheme -> {
                darkScheme
            }

            else -> {
                lightScheme
            }
        }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content,
    )
}
