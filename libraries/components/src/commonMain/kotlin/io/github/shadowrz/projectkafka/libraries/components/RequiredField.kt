package io.github.shadowrz.projectkafka.libraries.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import io.github.shadowrz.projectkafka.libraries.components.preview.PreviewGroups
import io.github.shadowrz.projectkafka.libraries.components.preview.ProjectKafkaPreview
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun RequiredField(modifier: Modifier = Modifier) {
    Text(
        "*",
        modifier = modifier,
        fontWeight = FontWeight.Bold,
    )
}

@Preview(name = "RequiredField", group = PreviewGroups.Form)
@Composable
internal fun PreviewRequiredField() =
    ProjectKafkaPreview {
        RequiredField()
    }
