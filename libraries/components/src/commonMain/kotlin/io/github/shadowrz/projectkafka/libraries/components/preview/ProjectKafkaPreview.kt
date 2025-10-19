package io.github.shadowrz.projectkafka.libraries.components.preview

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalInspectionMode
import com.slack.circuit.sharedelements.PreviewSharedElementTransitionLayout
import io.github.shadowrz.projectkafka.libraries.components.theme.ProjectKafkaTheme

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun ProjectKafkaPreview(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalInspectionMode provides true,
    ) {
        PreviewSharedElementTransitionLayout {
            ProjectKafkaTheme(
                darkTheme = darkTheme,
            ) {
                content()
            }
        }
    }
}
