package io.github.shadowrz.projectkafka.designsystem

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable

/**
 * Access theme tokens used in Project Kafka (based on Material 3 tokens).
 */
object KafkaTheme {
    /**
     * Retrieves the current Material 3 [ColorScheme]
     * at the call site's position in the hierarchy.
     */
    val materialColors: ColorScheme
        @Composable @ReadOnlyComposable
        get() = MaterialTheme.colorScheme

    /**
     * Retrieves the current Material 3 [Typography]
     * at the call site's position in the hierarchy.
     */
    val typography: Typography
        @Composable @ReadOnlyComposable
        get() = MaterialTheme.typography
}

@Composable
expect fun KafkaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    useSystemFont: Boolean = false,
    content: @Composable () -> Unit,
)
