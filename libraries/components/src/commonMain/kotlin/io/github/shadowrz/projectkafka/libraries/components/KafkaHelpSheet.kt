package io.github.shadowrz.projectkafka.libraries.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KafkaHelpSheet(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val uriHandler = LocalUriHandler.current

    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
    ) {
        HelpContent(
            onOpenExternalLink = { uriHandler.openUrlInCustomTab(it) },
        )
    }
}

@Composable
internal expect fun HelpContent(
    onOpenExternalLink: (String) -> Unit,
    modifier: Modifier = Modifier,
)
