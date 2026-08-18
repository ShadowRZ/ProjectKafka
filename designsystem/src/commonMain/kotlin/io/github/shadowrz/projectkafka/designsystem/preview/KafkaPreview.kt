package io.github.shadowrz.projectkafka.designsystem.preview

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.AndroidUiModes.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.AndroidUiModes.UI_MODE_TYPE_NORMAL
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers.RED_DOMINATED_EXAMPLE
import coil3.annotation.ExperimentalCoilApi
import io.github.shadowrz.projectkafka.designsystem.KafkaTheme
import io.github.shadowrz.projectkafka.designsystem.Surface

@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.FUNCTION)
@Preview(name = "Light")
@Preview(name = "Dark", uiMode = UI_MODE_NIGHT_YES or UI_MODE_TYPE_NORMAL)
@Preview(name = "Light - Red", wallpaper = RED_DOMINATED_EXAMPLE)
@Preview(name = "Dark - Red", uiMode = UI_MODE_NIGHT_YES or UI_MODE_TYPE_NORMAL, wallpaper = RED_DOMINATED_EXAMPLE)
annotation class KafkaPreview

@Suppress("detekt:ModifierMissing")
@OptIn(
    ExperimentalSharedTransitionApi::class,
    ExperimentalCoilApi::class,
)
@Composable
fun KafkaPreview(
    showBackground: Boolean = true,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    CompositionLocals {
        KafkaTheme(
            darkTheme = darkTheme,
            content = {
                if (showBackground) {
                    Surface(content = content)
                } else {
                    content()
                }
            },
        )
    }
}

@Composable internal expect fun CompositionLocals(content: @Composable () -> Unit)
