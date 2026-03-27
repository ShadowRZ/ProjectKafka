package io.github.shadowrz.projectkafka.designsystem

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import io.github.shadowrz.projectkafka.designsystem.preview.KafkaPreview

@Composable
fun ButtonColumn(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        content = content,
    )
}

@Composable
@PreviewLightDark
internal fun PreviewButtomColumn() =
    KafkaPreview {
        ButtonColumn {
            Button("Hello World", onClick = {}, modifier = Modifier.fillMaxWidth())
            OutlinedButton("Hello World", onClick = {}, modifier = Modifier.fillMaxWidth())
            FilledTonalButton("Hello World", onClick = {}, modifier = Modifier.fillMaxWidth())
            TextButton("Hello World", onClick = {}, modifier = Modifier.fillMaxWidth())
        }
    }
