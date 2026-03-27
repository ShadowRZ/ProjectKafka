package io.github.shadowrz.projectkafka.designsystem.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import io.github.shadowrz.projectkafka.designsystem.BackButton
import io.github.shadowrz.projectkafka.designsystem.Button
import io.github.shadowrz.projectkafka.designsystem.ButtonColumn
import io.github.shadowrz.projectkafka.designsystem.Text
import io.github.shadowrz.projectkafka.designsystem.TextButton
import io.github.shadowrz.projectkafka.designsystem.preview.KafkaPreview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlowStepPage(
    title: String,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    scrollable: Boolean = false,
    onBack: (() -> Unit)? = null,
    buttons: @Composable ColumnScope.() -> Unit = {},
    content: @Composable () -> Unit,
) {
    HeaderFooterPage(
        modifier = modifier,
        scrollable = scrollable,
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    if (onBack != null) {
                        BackButton(onClick = onBack)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
            )
        },
        header = {
            Column(
                modifier = Modifier.fillMaxWidth().widthIn(max = 480.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Text(
                    title,
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                )
                if (subtitle != null) {
                    Text(
                        subtitle,
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center,
                    )
                }
            }
        },
        content = content,
        footer = {
            ButtonColumn(
                modifier = Modifier
                    .windowInsetsPadding(
                        WindowInsets.navigationBars
                            .add(WindowInsets.displayCutout.only(WindowInsetsSides.Horizontal)),
                    ).imePadding()
                    .padding(16.dp),
            ) { buttons() }
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@PreviewLightDark
internal fun PreviewFlowStepPage() =
    KafkaPreview {
        FlowStepPage(
            title = "Title",
            subtitle = "Subtitle",
            buttons = {
                TextButton("Learn More", onClick = {})
                Button("Continue", onClick = {})
            },
            content = {
                Text(
                    "Content",
                    modifier = Modifier.fillMaxSize().wrapContentSize(),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineMedium,
                )
            },
        )
    }
