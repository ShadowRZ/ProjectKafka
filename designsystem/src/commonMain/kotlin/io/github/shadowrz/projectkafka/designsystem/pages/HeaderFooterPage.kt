package io.github.shadowrz.projectkafka.designsystem.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import io.github.shadowrz.projectkafka.designsystem.preview.KafkaPreview

@Composable
fun HeaderFooterPage(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(16.dp),
    containerColor: Color = MaterialTheme.colorScheme.background,
    scrollable: Boolean = false,
    background: @Composable () -> Unit = {},
    topBar: @Composable () -> Unit = {},
    header: @Composable () -> Unit = {},
    footer: @Composable () -> Unit = {},
    content: @Composable () -> Unit = {},
) {
    Scaffold(
        modifier = modifier,
        topBar = topBar,
        containerColor = containerColor,
    ) { insetsPadding ->
        val layoutDirection = LocalLayoutDirection.current
        val contentInsetsPadding = remember(insetsPadding, layoutDirection) {
            PaddingValues(
                start = insetsPadding.calculateStartPadding(layoutDirection),
                end = insetsPadding.calculateEndPadding(layoutDirection),
                top = insetsPadding.calculateTopPadding(),
            )
        }
        val footerInsetsPadding = remember(insetsPadding, layoutDirection) {
            PaddingValues(
                start = insetsPadding.calculateStartPadding(layoutDirection),
                end = insetsPadding.calculateEndPadding(layoutDirection),
                bottom = insetsPadding.calculateBottomPadding(),
            )
        }

        Box {
            background()

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding)
                    .consumeWindowInsets(insetsPadding)
                    .imePadding(),
            ) {
                // Content
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .run {
                            if (scrollable) {
                                verticalScroll(rememberScrollState()).height(IntrinsicSize.Max)
                            } else {
                                Modifier
                            }
                        }.padding(contentInsetsPadding)
                        .weight(1f, fill = true),
                ) {
                    header()
                    Box { content() }
                }
                // Footer
                Box(modifier = Modifier.fillMaxWidth().padding(footerInsetsPadding)) {
                    footer()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@PreviewLightDark
internal fun PreviewHeaderFooterPage() =
    KafkaPreview {
        HeaderFooterPage(
            header = {
                Text(
                    "Header",
                    modifier = Modifier.fillMaxWidth(),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineMedium,
                )
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
