package io.github.shadowrz.projectkafka.libraries.components.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Suppress("unused")
@Composable
actual fun ProjectKafkaTheme(
    darkTheme: Boolean,
    dynamicColor: Boolean,
    useSystemFont: Boolean,
    content: @Composable (() -> Unit),
) {
    val colorScheme = if (darkTheme) darkScheme else lightScheme
    val typography = if (useSystemFont) baseline else Typography

    MaterialTheme(
        colorScheme = colorScheme,
        typography = typography,
        content = content,
    )
}
