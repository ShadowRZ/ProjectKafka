package io.github.shadowrz.projectkafka.designsystem

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocal
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.font.FontFamily

/**
 * Project Kafka theme tokens.
 *
 * This is primarily intended for outside usages, so included modules don't have to
 * depend on Material 3 artifacts.
 */
@Immutable
data class KafkaTheme(
    val materialColors: KafkaColors,
    val typography: KafkaTypography,
    val shapes: KafkaShapes = KafkaShapes(),
) {
    /**
     * Derive a [KafkaTheme] based on [materialTheme].
     */
    internal constructor(materialTheme: MaterialTheme.Values) : this(
        materialColors = KafkaColors(materialTheme.colorScheme),
        typography = KafkaTypography(materialTheme.typography),
        shapes = KafkaShapes(materialTheme.shapes),
    )
    constructor() : this(materialTheme = MaterialTheme.Values())

    /**
     * Access theme tokens used in Project Kafka (based on Material 3 tokens).
     */
    companion object {
        /**
         * [CompositionLocal] providing Project Kafka's theme tokens throughout the hierarchy. You can use
         * properties in the companion object to access specific token types, for example [materialColors].
         * To provide a new value for this, use [KafkaTheme]. This API is exposed to allow retrieving
         * values from inside CompositionLocalConsumerModifierNode implementations - in most cases you
         * should use [materialColors] and other properties directly.
         */
        val LocalKafkaTheme: CompositionLocal<KafkaTheme> = LocalProvidableKafkaTheme

        /**
         * Retrieves the current [KafkaColors] at the call site's position in the hierarchy.
         */
        val materialColors: KafkaColors
            @Composable @ReadOnlyComposable
            get() = LocalKafkaTheme.current.materialColors

        /**
         * Retrieves the current [KafkaTypography] at the call site's position in the hierarchy.
         */
        val typography: KafkaTypography
            @Composable @ReadOnlyComposable
            get() = LocalKafkaTheme.current.typography

        /**
         * Retrieves the current [KafkaShapes] at the call site's position in the hierarchy.
         */
        val shapes: KafkaShapes
            @Composable @ReadOnlyComposable
            get() = LocalKafkaTheme.current.shapes
    }
}

/** Use [KafkaTheme.LocalKafkaTheme] to access this publicly. */
@Suppress("detekt:CompositionLocalAllowlist")
private val LocalProvidableKafkaTheme: ProvidableCompositionLocal<KafkaTheme> =
    staticCompositionLocalOf {
        KafkaTheme()
    }

@Composable
fun KafkaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    useSystemFont: Boolean = false,
    content: @Composable () -> Unit,
) {
    val colorScheme = materialColorScheme(darkTheme = darkTheme, dynamicColor = dynamicColor)
    val typography = Typography(fontFamily = kafkaTypographyFont(useSystemFont = useSystemFont))

    MaterialTheme(
        colorScheme = colorScheme,
        typography = typography,
    ) {
        CompositionLocalProvider(
            LocalProvidableKafkaTheme provides KafkaTheme(materialTheme = MaterialTheme.LocalMaterialTheme.current),
            content = content,
        )
    }
}

@Composable
internal expect fun materialColorScheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
): ColorScheme

@Composable
private fun kafkaTypographyFont(useSystemFont: Boolean = false): FontFamily =
    if (useSystemFont) FontFamily.Default else KafkaTypography.FontFamily
