package io.github.shadowrz.projectkafka.designsystem.preview

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import coil3.annotation.ExperimentalCoilApi
import com.slack.circuit.sharedelements.PreviewSharedElementTransitionLayout
import io.github.shadowrz.projectkafka.designsystem.KafkaTheme
import io.github.shadowrz.projectkafka.designsystem.Surface

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
    PreviewSharedElementTransitionLayout {
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
}

@Composable
internal expect fun CompositionLocals(content: @Composable () -> Unit)
