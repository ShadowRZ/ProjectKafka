package io.github.shadowrz.projectkafka.designsystem

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
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
    val colorScheme = if (darkTheme) darkScheme else lightScheme
    val typography = if (useSystemFont) baseline else Typography

    MaterialTheme(
        colorScheme = colorScheme,
        typography = typography,
        content = content,
    )
}
