package io.github.shadowrz.projectkafka.designsystem

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape

@Composable
fun Card(
    modifier: Modifier = Modifier,
    shape: Shape = CardDefaults.shape,
    border: BorderStroke? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    androidx.compose.material3.Card(
        modifier = modifier,
        shape = shape,
        border = border,
        content = content,
    )
}

@Composable
fun ElevatedCard(
    modifier: Modifier = Modifier,
    shape: Shape = CardDefaults.shape,
    content: @Composable ColumnScope.() -> Unit,
) {
    androidx.compose.material3.ElevatedCard(
        modifier = modifier,
        shape = shape,
        content = content,
    )
}
