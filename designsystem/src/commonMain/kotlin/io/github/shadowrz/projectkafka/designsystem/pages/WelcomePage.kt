package io.github.shadowrz.projectkafka.designsystem.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import io.github.shadowrz.projectkafka.designsystem.preview.KafkaPreview

@Composable
fun WelcomePage(
    modifier: Modifier = Modifier,
    contentAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    footer: @Composable () -> Unit = {},
    content: @Composable () -> Unit = {},
) {
    Surface(modifier = modifier.fillMaxSize()) {
        Column(modifier = Modifier.systemBarsPadding().padding(16.dp)) {
            Column(
                modifier = Modifier.weight(1f).fillMaxWidth(),
                horizontalAlignment = contentAlignment,
            ) {
                content()
            }
            Box(modifier = Modifier.fillMaxWidth()) {
                footer()
            }
        }
    }
}

@Composable
@PreviewLightDark
internal fun PreviewWelcomePage() =
    KafkaPreview {
        WelcomePage(
            content = {
                Text(
                    "Content",
                    modifier = Modifier.fillMaxSize().wrapContentSize(),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineMedium,
                )
            },
            footer = {
                Text(
                    "Footer",
                    modifier = Modifier.fillMaxWidth(),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineMedium,
                )
            },
        )
    }
